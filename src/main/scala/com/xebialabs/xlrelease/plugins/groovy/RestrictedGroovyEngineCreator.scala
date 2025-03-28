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

import com.xebialabs.xlrelease.script.groovy.GroovyEngineCreator
import grizzled.slf4j.Logging
import groovy.lang.{GroovyClassLoader, Script}
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl
import org.kohsuke.groovy.sandbox.SandboxTransformer
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

import javax.script.ScriptEngine

@Primary
@Component
class RestrictedGroovyEngineCreator extends GroovyEngineCreator with Logging {

  override def createEngine(restricted: Boolean): ScriptEngine = {
    if (restricted) {
      val secureConfig = new CompilerConfiguration
      secureConfig.addCompilationCustomizers(new SandboxTransformer)
      val groovyClassLoader = new GroovyClassLoader(getParentLoader, secureConfig)
      logger.debug(s"Groovy engine [restricted: $restricted] instance started.")
      new GroovyScriptEngineImpl(groovyClassLoader)
    } else {
      logger.debug(s"Groovy engine [restricted: $restricted] instance started.")
      new GroovyScriptEngineImpl()
    }
  }

  private def getParentLoader: ClassLoader = {
    // check whether thread context loader can "see" Groovy Script class
    val ctxtLoader = Thread.currentThread.getContextClassLoader
    try {
      val scriptClass = ctxtLoader.loadClass(classOf[Script].getName)
      //noinspection ScalaStyle
      if (scriptClass == classOf[Script]) return ctxtLoader
    } catch {
      case _: ClassNotFoundException => // ignore
    }
    // exception was thrown or we get wrong class
    classOf[Script].getClassLoader
  }
}
