<div align="center">

[![Discord][discord-shield]][discord-url]
[![GitHub][github-shield]][github-url]
[![Paypal][paypal-shield]][paypal-url]
</div>

---

### qlib-paper | A [Paper][paper-url] plugin library

---


**Disclaimer: I don't recommend using this library. This is personal project to help me learn Java and Paper API. I know this isn't optimized, and it's doing things incorrectly. This isn't supposed to be the best. 
I recommend using libraries like Configurate, Cloud/CommandAPI or many of the other standard libraries.*

That being said, if you'd like to give me any pointers... Make an issue or join me on [discord][discord-url]

---

- Custom API for Commands
  - Command Aliases, TabCompletions, Subcommands
- Config and Datafiles
- Includes [bStats][bstats-url]
- Includes [MorePersistentDataTypes][datatypes-url]

---

Add qPaperPlugin to your ``/pom.xml`` using [JitPack][jitpack-url]
```xml
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
```
```xml
        <dependency>
            <groupId>com.github.QarthO</groupId>
            <artifactId>qlib-paper</artifactId>
            <version>put-version-here</version>
            <scope>compile</scope>
        </dependency>
```
---

[discord-shield]: https://img.shields.io/badge/Discord-5865F2?logo=discord&logoColor=white&style=for-the-badge
[discord-url]: https://quartzdev.gg/discord/
[github-shield]: https://img.shields.io/badge/Source-181717?logo=github&logoColor=white&style=for-the-badge
[github-url]: https://github.com/QarthO/qPaperPlugin/
[paypal-shield]: https://img.shields.io/badge/Donate-00457C?logo=paypal&logoColor=white&style=for-the-badge
[paypal-url]: https://quartzdev.gg/paypal/
[jitpack-url]: https://jitpack.io/#QarthO/qPaperPlugin/
[paper-url]: https://papermc.io/
[bstats-url]: https:/bstats.org/
[datatypes-url]: https://github.com/mfnalex/MorePersistentDataTypes
