EssentialCmds
=============

**Currently not stable and under heavy development**

A minecraft plugin for Sponge platforms.
Contains many essential commands, features and other useful goodies.


## Links ##
* [Source]
* [Wiki]
* [Issues]
* [Website]

## License ##
This plugin is licensed under [MIT License].
This means that you are allowed to code in any way you would like.

## Prerequisites ##
* [Java] 8

## Contributing ##
I really appreciate if people contribute to the project.
I have a general set of [rules] I follow for my projects.
Do read through it if you do plan on contributing.

## Clone ##
The following steps will ensure your project is cloned properly

1. `git clone git@github.com:hsyyid/EssentialCmds.git`
2. `cd Foundations`

## Development Environment ##
__Note:__ If you do not have [Gradle] installed then use ./gradlew for Unix systems or Git Bash and gradlew.bat for
Windows systems in place of any 'gradle' command.

If you are a contributor, it is important that your development environment is setup properly. After cloning, as shown
above, follow the given steps for your ide:

#### [IntelliJ]

1. `gradle idea --refresh-dependencies`

#### [Eclipse]

1. `gradle eclipse --refresh-dependencies`

## Updating your Clone ##
__Note:__ If you do not have [Gradle] installed then use ./gradlew for Unix systems or Git Bash and gradlew.bat for
Windows systems in place of any 'gradle' command.

The following steps will update your clone with the official repo.

* `git pull`
* `gradle --refresh-dependencies`

## Building
__Note:__ If you do not have [Gradle] installed then use ./gradlew for Unix systems or Git Bash and gradlew.bat for
Windows systems in place of any 'gradle' command.

We use [Gradle] for Essence Sponge.

In order to build Essence Sponge you simply need to run the `gradle` command.
You can find the compiled JAR file in `./build/libs` labeled similarly to 'EssentialCmds-x.x.jar'.

[Source]: https://github.com/hsyyid/EssentialCmds
[Wiki]: https://github.com/hsyyid/EssentialCmds/wiki
[Issues]: https://github.com/hsyyid/EssentialCmds/issues
[Website]: http://essentialcmds.negafinity.com
[MIT License]: https://tldrlegal.com/license/mit-license
[Java]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[rules]: Contributors.md
[IntelliJ]: https://www.jetbrains.com/idea/
[Eclipse]: https://www.eclipse.org/
[Gradle]: https://www.gradle.org/