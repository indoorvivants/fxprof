# WIP Firefox Profiler format and tracer for Scala

This project implements the JSON format used by Firefox Profiler, and 
uses that format to provide a simple `Tracer`, allowing to record nested spans in 
a format that can be visualised on https://profiler.firefox.com

See [./fxprof-sample/src/main/scala/sample.scala](sample) for usage:
  1. Run `buildSample` in SBT shell
  2. Load the `sample-profile.json` (will be created at the root of the project) into Firefox Profiler
  3. My development workflow usually involves running some live reload server (with CORS), and using "Load from URL" in Firefox Profiler – this allows me to continuously rebuild the profile file and I only need to reload the Profiler tab to see the changes

```scala 
// SBT
libraryDependencies += "com.indoorvivants" %% "fxprof-tracer" % "VERSION"
// Scala CLI 
//> using dep com.indoorvivants::fxprof-tracer::VERSION
```

All modules are 

Caveats:
  1. Current tracer is single threaded
  2. The output looks right-ish, but not as rich as something like what [samply](https://github.com/mstange/samply) can produce
  3. At the moment profiles can only be written, not read
  
This work relies on a lot of trial and error because the firefox profiler format is 
sparsely documented, and mostly defined in a typescript file. 
To that end, we use a very crude code generator along with manual extensions.

Jsoniter is used for JSON serialisation.
