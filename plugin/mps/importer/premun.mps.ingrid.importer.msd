<?xml version="1.0" encoding="UTF-8"?>
<solution name="premun.mps.ingrid.importer" uuid="f0276ed1-44e5-4463-9cfe-cf15618e4974" moduleVersion="0" pluginKind="PLUGIN_OTHER" compileInMPS="true">
  <models>
    <modelRoot contentPath="${module}" type="default">
      <sourceRoot location="models" />
    </modelRoot>
    <modelRoot contentPath="${INGRID_HOME}/plugin/idea/importer" type="java_source_stubs">
      <sourceRoot location="src" />
    </modelRoot>
  </models>
  <sourcePath>
    <source path="${INGRID_HOME}/plugin/idea/importer/src" />
  </sourcePath>
  <dependencies>
    <dependency reexport="false">f0276ed1-44e5-4463-9cfe-cf15618e4974(premun.mps.ingrid.importer)</dependency>
    <dependency reexport="false">a34e01c8-8c6f-48c5-a512-bb294cca062e(premun.mps.ingrid.model)</dependency>
    <dependency reexport="false">9450b346-4f8d-4243-9894-05b73fc17e09(premun.mps.ingrid.library)</dependency>
    <dependency reexport="false">66288791-e621-45d1-bebf-408d2568bbe6(premun.mps.ingrid.parser)</dependency>
    <dependency reexport="false">8865b7a8-5271-43d3-884c-6fd1d9cfdd34(MPS.OpenAPI)</dependency>
  </dependencies>
  <languageVersions>
    <language slang="l:f3061a53-9226-4cc5-a443-f952ceaf5816:jetbrains.mps.baseLanguage" version="4" />
    <language slang="l:ed6d7656-532c-4bc2-81d1-af945aeb8280:jetbrains.mps.baseLanguage.blTypes" version="0" />
    <language slang="l:ceab5195-25ea-4f22-9b92-103b95ca8c0c:jetbrains.mps.lang.core" version="1" />
    <language slang="l:9ded098b-ad6a-4657-bfd9-48636cfe8bc3:jetbrains.mps.lang.traceable" version="0" />
  </languageVersions>
  <dependencyVersions>
    <module reference="3f233e7f-b8a6-46d2-a57f-795d56775243(Annotations)" version="0" />
    <module reference="6354ebe7-c22a-4a0f-ac54-50b52ab9b065(JDK)" version="0" />
    <module reference="8865b7a8-5271-43d3-884c-6fd1d9cfdd34(MPS.OpenAPI)" version="0" />
    <module reference="f0276ed1-44e5-4463-9cfe-cf15618e4974(premun.mps.ingrid.importer)" version="0" />
    <module reference="9450b346-4f8d-4243-9894-05b73fc17e09(premun.mps.ingrid.library)" version="0" />
    <module reference="a34e01c8-8c6f-48c5-a512-bb294cca062e(premun.mps.ingrid.model)" version="0" />
    <module reference="66288791-e621-45d1-bebf-408d2568bbe6(premun.mps.ingrid.parser)" version="0" />
  </dependencyVersions>
</solution>

