fmt:
	scalafmt *.scala generated

generate-sample:
	scala-cli run fxprof-sample fxprof-format -- $(ARGS)

watch-generate-sample:
	scala-cli run -w fxprof-sample fxprof-format -- $(ARGS)


codegen:
	scala-cli run fxprof-codegen -- ./fxprof-format/generated
