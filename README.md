# xlr-groovy-sandbox-plugin

[![License: MIT][xlr-groovy-sandbox-plugin-license-image]][xlr-groovy-sandbox-plugin-license-url]
![Github All Releases][xlr-groovy-sandbox-plugin-downloads-image]

## Overview

The **xlr-groovy-sandbox-plugin** is designed for use with **Digital.ai Release 25.1.x**.

In **Digital.ai Release 25.1.x**, the Groovy sandbox was removed due to the deprecation of the Java Security Manager in **Java 17**.

For customers who still require the Groovy sandbox in **25.1.x**, this plugin provides a fallback mechanism to reintroduce the sandbox. It is recommended to use
**task access restrictions** for Groovy script tasks as described in
the [Digital.ai Release documentation](https://docs.digital.ai/release/docs/how-to/configure-task-access).

## Features

- Restores the Groovy sandbox functionality for **Digital.ai Release 25.1.x**.
- Allows customers to continue using Groovy scripts with sandboxing.

## Compatibility

This plugin **only works with Digital.ai Release 25.1.x** and is not compatible with earlier or later versions.

## Installation

To install the plugin:

1. Download the latest JAR file from the [Releases](https://github.com/xebialabs-community/xlr-groovy-sandbox-plugin/releases) page.
2. Copy the JAR file into the following directory on your Digital.ai Release server:
   ```
   DAI_RELEASE_SERVER/plugins/__local__
   ```
3. Restart the Digital.ai Release server.

## Usage

Once installed, the plugin will automatically enable the Groovy sandbox in **Digital.ai Release 25.1.x**.

For additional security, consider restricting access to the Groovy script task using the **task access feature** mentioned in
the [official documentation](https://docs.digital.ai/release/docs/how-to/configure-task-access).

## Notes

- This plugin is intended as a **temporary fallback solution** while migrating to the new mechanism.
- Future versions of Digital.ai Release may introduce alternative approaches, so plan accordingly.

## License

This project is licensed under the [MIT License](LICENSE).

---

[xlr-groovy-sandbox-plugin-license-image]: https://img.shields.io/badge/License-MIT-yellow.svg
[xlr-groovy-sandbox-plugin-license-url]: https://opensource.org/licenses/MIT
[xlr-groovy-sandbox-plugin-downloads-image]: https://img.shields.io/github/downloads/xebialabs-community/xlr-groovy-sandbox-plugin/total.svg
