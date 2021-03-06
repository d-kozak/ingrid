<?xml version="1.0" encoding="UTF-8"?>
<solution name="premun.mps.ingrid.transformer" uuid="e160cd38-793b-429d-84fd-f5e8dc0825dd" moduleVersion="0" compileInMPS="true">
  <models>
    <modelRoot contentPath="${module}" type="default">
      <sourceRoot location="models" />
    </modelRoot>
    <modelRoot contentPath="${INGRID_HOME}/plugin/idea/transformer" type="java_source_stubs">
      <sourceRoot location="src" />
    </modelRoot>
  </models>
  <sourcePath>
    <source path="${INGRID_HOME}/plugin/idea/transformer/src" />
  </sourcePath>
  <dependencies>
    <dependency reexport="false">f3061a53-9226-4cc5-a443-f952ceaf5816(jetbrains.mps.baseLanguage)</dependency>
    <dependency reexport="false">a34e01c8-8c6f-48c5-a512-bb294cca062e(premun.mps.ingrid.model)</dependency>
    <dependency reexport="false">66288791-e621-45d1-bebf-408d2568bbe6(premun.mps.ingrid.parser)</dependency>
    <dependency reexport="false">640165f1-e62d-415b-b300-3d0903929449(premun.mps.ingrid.formatter)</dependency>
  </dependencies>
  <languageVersions>
    <language slang="l:f3061a53-9226-4cc5-a443-f952ceaf5816:jetbrains.mps.baseLanguage" version="6" />
    <language slang="l:ceab5195-25ea-4f22-9b92-103b95ca8c0c:jetbrains.mps.lang.core" version="1" />
    <language slang="l:9ded098b-ad6a-4657-bfd9-48636cfe8bc3:jetbrains.mps.lang.traceable" version="0" />
  </languageVersions>
  <dependencyVersions>
    <module reference="3f233e7f-b8a6-46d2-a57f-795d56775243(Annotations)" version="0" />
    <module reference="6354ebe7-c22a-4a0f-ac54-50b52ab9b065(JDK)" version="0" />
    <module reference="6ed54515-acc8-4d1e-a16c-9fd6cfe951ea(MPS.Core)" version="0" />
    <module reference="8865b7a8-5271-43d3-884c-6fd1d9cfdd34(MPS.OpenAPI)" version="0" />
    <module reference="f3061a53-9226-4cc5-a443-f952ceaf5816(jetbrains.mps.baseLanguage)" version="0" />
    <module reference="e39e4a59-8cb6-498e-860e-8fa8361c0d90(jetbrains.mps.baseLanguage.scopes)" version="0" />
    <module reference="2d3c70e9-aab2-4870-8d8d-6036800e4103(jetbrains.mps.kernel)" version="0" />
    <module reference="ceab5195-25ea-4f22-9b92-103b95ca8c0c(jetbrains.mps.lang.core)" version="0" />
    <module reference="9ded098b-ad6a-4657-bfd9-48636cfe8bc3(jetbrains.mps.lang.traceable)" version="0" />
    <module reference="4db458f1-215f-424c-8756-4cc4b0227697(org.antlr)" version="0" />
    <module reference="640165f1-e62d-415b-b300-3d0903929449(premun.mps.ingrid.formatter)" version="0" />
    <module reference="a34e01c8-8c6f-48c5-a512-bb294cca062e(premun.mps.ingrid.model)" version="0" />
    <module reference="66288791-e621-45d1-bebf-408d2568bbe6(premun.mps.ingrid.parser)" version="0" />
    <module reference="e160cd38-793b-429d-84fd-f5e8dc0825dd(premun.mps.ingrid.transformer)" version="0" />
  </dependencyVersions>
</solution>

