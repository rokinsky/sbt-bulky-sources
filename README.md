# sbt-bulky-sources

![Scala CI](https://github.com/rokinsky/sbt-bulky-sources/workflows/Scala%20CI/badge.svg)
[![codecov](https://codecov.io/gh/rokinsky/sbt-bulky-sources/branch/master/graph/badge.svg?token=RAZKN2QQ52)](https://codecov.io/gh/rokinsky/sbt-bulky-sources)

The plugin works with main and test sources and shows a list of "large" sources, sorted in descending order by `LOC`([lines of code](https://en.wikipedia.org/wiki/Source_lines_of_code)) value.

## Usage

`<threshold>` is an optional unsigned integer specifying the minimum `LOC` value for files

### Compile configuration
```bash
sbt show bulkySources <threshold>
```

Output example:
```
[info] * (130, .../src/main/scala/../../../A.scala)
[info] * (100, .../src/main/scala/../../../B.scala)
```

### Test configuration
```bash
sbt show test:bulkySources <threshold>
```

Output example:
```
[info] * (170, .../src/test/scala/../../../Y.scala)
[info] * (110, .../src/test/scala/../../../Z.scala)
```

## Settings

* `bulkyThresholdInLines: Int` — a fallback `<threshold>` value for empty input, default is `100`

## Installation
The plugin isn't published on any public repository. Stay tuned.
