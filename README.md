<div align="center">

[![Discord][discord-shield]][discord-url]
[![GitHub][github-shield]][github-url]
[![Paypal][paypal-shield]][paypal-url]
</div>

---

### qlib-paper | A [Paper][paper-url] plugin library

### **I don't ever recommend using this library. This is more just a fun little personal project to learn java and plugins for me. I'm certain it's not opitmized, and its doing things the completely wrong way. I recommend using existing libraries like Configurate for config/data files, and Cloud or CommandAPI for command handling*.
That being said if you'd like to give me pointers or help feel free to create an issue or discussion post on the repo!
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
