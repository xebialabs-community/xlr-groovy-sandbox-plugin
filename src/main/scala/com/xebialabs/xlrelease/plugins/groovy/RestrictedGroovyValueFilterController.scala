/**
 * Copyright 2025 DIGITAL.AI
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.xebialabs.xlrelease.plugins.groovy

import com.xebialabs.xlrelease.script.groovy.GroovyValueFilterController
import com.xebialabs.xlrelease.script.security.SandboxSupport
import grizzled.slf4j.Logging
import groovy.lang.{Closure, Script}
import org.kohsuke.groovy.sandbox.GroovyValueFilter
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class RestrictedGroovyValueFilterController extends GroovyValueFilter with GroovyValueFilterController with SandboxSupport with Logging {
  override def register(): Unit = {
    super.register()
  }

  override def unregister(): Unit = {
    super.unregister()
  }

  override def filterReceiver(receiver: AnyRef): AnyRef = {
    if (isExternalReceiver(receiver)) {
      val target = getTarget(receiver)
      if (!hasClassAccess(target)) {
        val message = s"Loading the restricted class [$target] is not allowed. Please update the script.policy file if you wish to grant access."
        logger.warn(message)
        throw new SecurityException(message)
      }
    }
    receiver
  }

  private def isExternalReceiver(receiver: AnyRef): Boolean = {
    receiver != null && !receiver.isInstanceOf[Closure[_]] && !receiver.isInstanceOf[Script]
  }

  private def getTarget(receiver: AnyRef) = {
    (receiver match {
      case clazz: Class[_] => clazz
      case _ => receiver.getClass
    }) match {
      case clazz if clazz.getPackage == null || clazz.getPackage.getName == "" => s"default.${clazz.getName}"
      case clazz => clazz.getName
    }
  }
}
