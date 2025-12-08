fmt:
	scalafmt *.scala generated

generate-sample:
	scala-cli run fxprof-sample fxprof-format -- ./profile.json

watch-generate-sample:
	scala-cli run -w fxprof-sample fxprof-format -- --out ./profile.json


codegen:
	scala-cli run fxprof-codegen -- ./fxprof-format/generated

watch-codegen:
	scala-cli run -w fxprof-codegen -- ./fxprof-format/generated
