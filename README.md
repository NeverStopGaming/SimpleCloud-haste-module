<h1 align="center">
  SimpleCloud Haste Module
</h1>

<h3 align="center">
  A SimpleCloud Module for uploading the current logs of your services to hastebin
</h3>

## Usage

`cloud haste <service>` will upload the logs of the given service to hastebin.

`cloud haste current` will upload the logs of the current service to hastebin.

`cloud support` quickly give the SimpleCloud Team a overview of your system and it's errors.

## Installation

<ol>
    <li><a href="https://github.com/NeverStopGaming/SimpleCloud-haste-module/releases/download/v1.1/SimpleCloud-Haste-Module-1.1.jar">Download the Jar file</a> or <a href="#Compile">Compile it yourself</a></li>
    <li>move the file to the modules folder(located in the rootdir/modules)</li>
    <li>reload or restart your cloud</li>
    <li>Have fun debugging your crappy code :^)</li>
</ol>

<h2 id="Compile">Compile it yourself</h2>

### Intellij Idea

`Ctrl + E >> Gradle >> Tasks >> build >> build`

### Linux

`./gradlew build`

### Windows

`.\gradlew.bat build`
