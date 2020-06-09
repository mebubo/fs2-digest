An attempt to use fold-like transformations on fs2 streams in a composable fashion. Enables computing multiple aggregates like hash or length, while consuming the stream only once.

Example output:

```
$ sbt "run build.sbt"
sha256: c203744d70374e6881534f8e4ee3813b3614619291a2e7a4bc9a1717cee43fca
sha512: dc66f0b59d33bdacb6867fb7a59793055823122c45e2816c0b70464e73342f0efa7b8439bbc307c1526b99413148152cbaaf30031b25cb7e895d7a2c2b522437
length: 136
```
