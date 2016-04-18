<?xml version="1.0" encoding="UTF-8"?>
<model ref="r:c9ec5779-d606-46da-9a30-1aecd7b674d7(premun.mps.ingrid.plugin.library)">
  <persistence version="9" />
  <languages>
    <use id="f3061a53-9226-4cc5-a443-f952ceaf5816" name="jetbrains.mps.baseLanguage" version="4" />
    <use id="7866978e-a0f0-4cc7-81bc-4d213d9375e1" name="jetbrains.mps.lang.smodel" version="2" />
    <use id="3a13115c-633c-4c5c-bbcc-75c4219e9555" name="jetbrains.mps.lang.quotation" version="0" />
    <use id="18bc6592-03a6-4e29-a83a-7ff23bde13ba" name="jetbrains.mps.lang.editor" version="3" />
    <use id="83888646-71ce-4f1c-9c53-c54016f6ad4f" name="jetbrains.mps.baseLanguage.collections" version="0" />
    <use id="f2801650-65d5-424e-bb1b-463a8781b786" name="jetbrains.mps.baseLanguage.javadoc" version="2" />
    <use id="b83431fe-5c8f-40bc-8a36-65e25f4dd253" name="jetbrains.mps.lang.textGen" version="0" />
  </languages>
  <imports>
    <import index="tpc2" ref="r:00000000-0000-4000-0000-011c8959029e(jetbrains.mps.lang.editor.structure)" />
    <import index="tpck" ref="r:00000000-0000-4000-0000-011c89590288(jetbrains.mps.lang.core.structure)" />
    <import index="mhbf" ref="8865b7a8-5271-43d3-884c-6fd1d9cfdd34/java:org.jetbrains.mps.openapi.model(MPS.OpenAPI/)" />
    <import index="tpce" ref="r:00000000-0000-4000-0000-011c89590292(jetbrains.mps.lang.structure.structure)" />
    <import index="twe9" ref="r:28e6d713-c3c3-493e-8d97-e9a2c49f28ce(jetbrains.mps.lang.structure.util)" />
    <import index="33ny" ref="6354ebe7-c22a-4a0f-ac54-50b52ab9b065/java:java.util(JDK/)" />
    <import index="2omo" ref="r:de0d3c0c-d049-41cf-bbf9-a920ba513cd3(jetbrains.mps.lang.textGen.structure)" />
    <import index="tpee" ref="r:00000000-0000-4000-0000-011c895902ca(jetbrains.mps.baseLanguage.structure)" />
    <import index="kr2q" ref="66ae852b-d084-4d2e-b9bc-dad381ea9aea/java:premun.mps.ingrid.parser.grammar(premun.mps.ingrid.plugin/)" />
    <import index="tpek" ref="r:00000000-0000-4000-0000-011c895902c0(jetbrains.mps.baseLanguage.behavior)" />
    <import index="wyt6" ref="6354ebe7-c22a-4a0f-ac54-50b52ab9b065/java:java.lang(JDK/)" implicit="true" />
    <import index="tp25" ref="r:00000000-0000-4000-0000-011c89590301(jetbrains.mps.lang.smodel.structure)" implicit="true" />
  </imports>
  <registry>
    <language id="18bc6592-03a6-4e29-a83a-7ff23bde13ba" name="jetbrains.mps.lang.editor">
      <concept id="1071666914219" name="jetbrains.mps.lang.editor.structure.ConceptEditorDeclaration" flags="ig" index="24kQdi" />
      <concept id="1140524381322" name="jetbrains.mps.lang.editor.structure.CellModel_ListWithRole" flags="ng" index="2czfm3">
        <child id="1140524464359" name="emptyCellModel" index="2czzBI" />
      </concept>
      <concept id="1237303669825" name="jetbrains.mps.lang.editor.structure.CellLayout_Indent" flags="nn" index="l2Vlx" />
      <concept id="1080736578640" name="jetbrains.mps.lang.editor.structure.BaseEditorComponent" flags="ig" index="2wURMF">
        <child id="1080736633877" name="cellModel" index="2wV5jI" />
      </concept>
      <concept id="1073389446423" name="jetbrains.mps.lang.editor.structure.CellModel_Collection" flags="sn" stub="3013115976261988961" index="3EZMnI">
        <child id="1106270802874" name="cellLayout" index="2iSdaV" />
      </concept>
      <concept id="1073389577006" name="jetbrains.mps.lang.editor.structure.CellModel_Constant" flags="sn" stub="3610246225209162225" index="3F0ifn">
        <property id="1073389577007" name="text" index="3F0ifm" />
      </concept>
      <concept id="1073389658414" name="jetbrains.mps.lang.editor.structure.CellModel_Property" flags="sg" stub="730538219796134133" index="3F0A7n" />
      <concept id="1073389882823" name="jetbrains.mps.lang.editor.structure.CellModel_RefNode" flags="sg" stub="730538219795960754" index="3F1sOY" />
      <concept id="1073390211982" name="jetbrains.mps.lang.editor.structure.CellModel_RefNodeList" flags="sg" stub="2794558372793454595" index="3F2HdR" />
    </language>
    <language id="f3061a53-9226-4cc5-a443-f952ceaf5816" name="jetbrains.mps.baseLanguage">
      <concept id="1080223426719" name="jetbrains.mps.baseLanguage.structure.OrExpression" flags="nn" index="22lmx$" />
      <concept id="1082485599095" name="jetbrains.mps.baseLanguage.structure.BlockStatement" flags="nn" index="9aQIb">
        <child id="1082485599096" name="statements" index="9aQI4" />
      </concept>
      <concept id="1215693861676" name="jetbrains.mps.baseLanguage.structure.BaseAssignmentExpression" flags="nn" index="d038R">
        <child id="1068498886297" name="rValue" index="37vLTx" />
        <child id="1068498886295" name="lValue" index="37vLTJ" />
      </concept>
      <concept id="4836112446988635817" name="jetbrains.mps.baseLanguage.structure.UndefinedType" flags="in" index="2jxLKc" />
      <concept id="1202948039474" name="jetbrains.mps.baseLanguage.structure.InstanceMethodCallOperation" flags="nn" index="liA8E" />
      <concept id="1465982738277781862" name="jetbrains.mps.baseLanguage.structure.PlaceholderMember" flags="ng" index="2tJIrI" />
      <concept id="1239714755177" name="jetbrains.mps.baseLanguage.structure.AbstractUnaryNumberOperation" flags="nn" index="2$Kvd9">
        <child id="1239714902950" name="expression" index="2$L3a6" />
      </concept>
      <concept id="1154032098014" name="jetbrains.mps.baseLanguage.structure.AbstractLoopStatement" flags="nn" index="2LF5Ji">
        <child id="1154032183016" name="body" index="2LFqv$" />
      </concept>
      <concept id="1197027756228" name="jetbrains.mps.baseLanguage.structure.DotExpression" flags="nn" index="2OqwBi">
        <child id="1197027771414" name="operand" index="2Oq$k0" />
        <child id="1197027833540" name="operation" index="2OqNvi" />
      </concept>
      <concept id="1197029447546" name="jetbrains.mps.baseLanguage.structure.FieldReferenceOperation" flags="nn" index="2OwXpG">
        <reference id="1197029500499" name="fieldDeclaration" index="2Oxat5" />
      </concept>
      <concept id="1083245097125" name="jetbrains.mps.baseLanguage.structure.EnumClass" flags="ig" index="Qs71p">
        <child id="1083245396908" name="enumConstant" index="Qtgdg" />
      </concept>
      <concept id="1083245299891" name="jetbrains.mps.baseLanguage.structure.EnumConstantDeclaration" flags="ig" index="QsSxf" />
      <concept id="1083260308424" name="jetbrains.mps.baseLanguage.structure.EnumConstantReference" flags="nn" index="Rm8GO">
        <reference id="1083260308426" name="enumConstantDeclaration" index="Rm8GQ" />
        <reference id="1144432896254" name="enumClass" index="1Px2BO" />
      </concept>
      <concept id="1145552977093" name="jetbrains.mps.baseLanguage.structure.GenericNewExpression" flags="nn" index="2ShNRf">
        <child id="1145553007750" name="creator" index="2ShVmc" />
      </concept>
      <concept id="1070475354124" name="jetbrains.mps.baseLanguage.structure.ThisExpression" flags="nn" index="Xjq3P" />
      <concept id="1070475926800" name="jetbrains.mps.baseLanguage.structure.StringLiteral" flags="nn" index="Xl_RD">
        <property id="1070475926801" name="value" index="Xl_RC" />
      </concept>
      <concept id="1081236700938" name="jetbrains.mps.baseLanguage.structure.StaticMethodDeclaration" flags="ig" index="2YIFZL" />
      <concept id="1081236700937" name="jetbrains.mps.baseLanguage.structure.StaticMethodCall" flags="nn" index="2YIFZM">
        <reference id="1144433194310" name="classConcept" index="1Pybhc" />
      </concept>
      <concept id="1164991038168" name="jetbrains.mps.baseLanguage.structure.ThrowStatement" flags="nn" index="YS8fn">
        <child id="1164991057263" name="throwable" index="YScLw" />
      </concept>
      <concept id="1081256982272" name="jetbrains.mps.baseLanguage.structure.InstanceOfExpression" flags="nn" index="2ZW3vV">
        <child id="1081256993305" name="classType" index="2ZW6by" />
        <child id="1081256993304" name="leftExpression" index="2ZW6bz" />
      </concept>
      <concept id="1070534058343" name="jetbrains.mps.baseLanguage.structure.NullLiteral" flags="nn" index="10Nm6u" />
      <concept id="1070534370425" name="jetbrains.mps.baseLanguage.structure.IntegerType" flags="in" index="10Oyi0" />
      <concept id="1070534644030" name="jetbrains.mps.baseLanguage.structure.BooleanType" flags="in" index="10P_77" />
      <concept id="1070534934090" name="jetbrains.mps.baseLanguage.structure.CastExpression" flags="nn" index="10QFUN">
        <child id="1070534934091" name="type" index="10QFUM" />
        <child id="1070534934092" name="expression" index="10QFUP" />
      </concept>
      <concept id="1068390468200" name="jetbrains.mps.baseLanguage.structure.FieldDeclaration" flags="ig" index="312cEg">
        <property id="8606350594693632173" name="isTransient" index="eg7rD" />
        <property id="1240249534625" name="isVolatile" index="34CwA1" />
      </concept>
      <concept id="1068390468198" name="jetbrains.mps.baseLanguage.structure.ClassConcept" flags="ig" index="312cEu" />
      <concept id="1068431474542" name="jetbrains.mps.baseLanguage.structure.VariableDeclaration" flags="ng" index="33uBYm">
        <property id="1176718929932" name="isFinal" index="3TUv4t" />
        <child id="1068431790190" name="initializer" index="33vP2m" />
      </concept>
      <concept id="1068431790191" name="jetbrains.mps.baseLanguage.structure.Expression" flags="nn" index="33vP2n" />
      <concept id="1068498886296" name="jetbrains.mps.baseLanguage.structure.VariableReference" flags="nn" index="37vLTw">
        <reference id="1068581517664" name="variableDeclaration" index="3cqZAo" />
      </concept>
      <concept id="1068498886292" name="jetbrains.mps.baseLanguage.structure.ParameterDeclaration" flags="ir" index="37vLTG" />
      <concept id="1068498886294" name="jetbrains.mps.baseLanguage.structure.AssignmentExpression" flags="nn" index="37vLTI" />
      <concept id="1225271177708" name="jetbrains.mps.baseLanguage.structure.StringType" flags="in" index="17QB3L" />
      <concept id="4972933694980447171" name="jetbrains.mps.baseLanguage.structure.BaseVariableDeclaration" flags="ng" index="19Szcq">
        <child id="5680397130376446158" name="type" index="1tU5fm" />
      </concept>
      <concept id="1068580123132" name="jetbrains.mps.baseLanguage.structure.BaseMethodDeclaration" flags="ng" index="3clF44">
        <property id="4276006055363816570" name="isSynchronized" index="od$2w" />
        <property id="1181808852946" name="isFinal" index="DiZV1" />
        <child id="1068580123133" name="returnType" index="3clF45" />
        <child id="1068580123134" name="parameter" index="3clF46" />
        <child id="1068580123135" name="body" index="3clF47" />
      </concept>
      <concept id="1068580123165" name="jetbrains.mps.baseLanguage.structure.InstanceMethodDeclaration" flags="ig" index="3clFb_" />
      <concept id="1068580123152" name="jetbrains.mps.baseLanguage.structure.EqualsExpression" flags="nn" index="3clFbC" />
      <concept id="1068580123155" name="jetbrains.mps.baseLanguage.structure.ExpressionStatement" flags="nn" index="3clFbF">
        <child id="1068580123156" name="expression" index="3clFbG" />
      </concept>
      <concept id="1068580123157" name="jetbrains.mps.baseLanguage.structure.Statement" flags="nn" index="3clFbH" />
      <concept id="1068580123159" name="jetbrains.mps.baseLanguage.structure.IfStatement" flags="nn" index="3clFbJ">
        <child id="1082485599094" name="ifFalseStatement" index="9aQIa" />
        <child id="1068580123160" name="condition" index="3clFbw" />
        <child id="1068580123161" name="ifTrue" index="3clFbx" />
        <child id="1206060520071" name="elsifClauses" index="3eNLev" />
      </concept>
      <concept id="1068580123136" name="jetbrains.mps.baseLanguage.structure.StatementList" flags="sn" stub="5293379017992965193" index="3clFbS">
        <child id="1068581517665" name="statement" index="3cqZAp" />
      </concept>
      <concept id="1068580123137" name="jetbrains.mps.baseLanguage.structure.BooleanConstant" flags="nn" index="3clFbT">
        <property id="1068580123138" name="value" index="3clFbU" />
      </concept>
      <concept id="1068580123140" name="jetbrains.mps.baseLanguage.structure.ConstructorDeclaration" flags="ig" index="3clFbW" />
      <concept id="1068580320020" name="jetbrains.mps.baseLanguage.structure.IntegerConstant" flags="nn" index="3cmrfG">
        <property id="1068580320021" name="value" index="3cmrfH" />
      </concept>
      <concept id="1068581242878" name="jetbrains.mps.baseLanguage.structure.ReturnStatement" flags="nn" index="3cpWs6">
        <child id="1068581517676" name="expression" index="3cqZAk" />
      </concept>
      <concept id="1068581242864" name="jetbrains.mps.baseLanguage.structure.LocalVariableDeclarationStatement" flags="nn" index="3cpWs8">
        <child id="1068581242865" name="localVariableDeclaration" index="3cpWs9" />
      </concept>
      <concept id="1068581242863" name="jetbrains.mps.baseLanguage.structure.LocalVariableDeclaration" flags="nr" index="3cpWsn" />
      <concept id="1068581517677" name="jetbrains.mps.baseLanguage.structure.VoidType" flags="in" index="3cqZAl" />
      <concept id="1206060495898" name="jetbrains.mps.baseLanguage.structure.ElsifClause" flags="ng" index="3eNFk2">
        <child id="1206060619838" name="condition" index="3eO9$A" />
        <child id="1206060644605" name="statementList" index="3eOfB_" />
      </concept>
      <concept id="1079359253375" name="jetbrains.mps.baseLanguage.structure.ParenthesizedExpression" flags="nn" index="1eOMI4">
        <child id="1079359253376" name="expression" index="1eOMHV" />
      </concept>
      <concept id="1081506762703" name="jetbrains.mps.baseLanguage.structure.GreaterThanExpression" flags="nn" index="3eOSWO" />
      <concept id="1204053956946" name="jetbrains.mps.baseLanguage.structure.IMethodCall" flags="ng" index="1ndlxa">
        <reference id="1068499141037" name="baseMethodDeclaration" index="37wK5l" />
        <child id="1068499141038" name="actualArgument" index="37wK5m" />
      </concept>
      <concept id="1212685548494" name="jetbrains.mps.baseLanguage.structure.ClassCreator" flags="nn" index="1pGfFk" />
      <concept id="1107461130800" name="jetbrains.mps.baseLanguage.structure.Classifier" flags="ng" index="3pOWGL">
        <child id="5375687026011219971" name="member" index="jymVt" unordered="true" />
      </concept>
      <concept id="7812454656619025416" name="jetbrains.mps.baseLanguage.structure.MethodDeclaration" flags="ng" index="1rXfSm">
        <property id="8355037393041754995" name="isNative" index="2aFKle" />
      </concept>
      <concept id="7812454656619025412" name="jetbrains.mps.baseLanguage.structure.LocalMethodCall" flags="nn" index="1rXfSq" />
      <concept id="1107535904670" name="jetbrains.mps.baseLanguage.structure.ClassifierType" flags="in" index="3uibUv">
        <reference id="1107535924139" name="classifier" index="3uigEE" />
        <child id="1109201940907" name="parameter" index="11_B2D" />
      </concept>
      <concept id="1081773326031" name="jetbrains.mps.baseLanguage.structure.BinaryOperation" flags="nn" index="3uHJSO">
        <child id="1081773367579" name="rightExpression" index="3uHU7w" />
        <child id="1081773367580" name="leftExpression" index="3uHU7B" />
      </concept>
      <concept id="1214918800624" name="jetbrains.mps.baseLanguage.structure.PostfixIncrementExpression" flags="nn" index="3uNrnE" />
      <concept id="1081855346303" name="jetbrains.mps.baseLanguage.structure.BreakStatement" flags="nn" index="3zACq4" />
      <concept id="1178549954367" name="jetbrains.mps.baseLanguage.structure.IVisible" flags="ng" index="1B3ioH">
        <child id="1178549979242" name="visibility" index="1B3o_S" />
      </concept>
      <concept id="1208623485264" name="jetbrains.mps.baseLanguage.structure.AbstractOperation" flags="nn" index="1B$H19" />
      <concept id="1144226303539" name="jetbrains.mps.baseLanguage.structure.ForeachStatement" flags="nn" index="1DcWWT">
        <child id="1144226360166" name="iterable" index="1DdaDG" />
      </concept>
      <concept id="1144230876926" name="jetbrains.mps.baseLanguage.structure.AbstractForStatement" flags="nn" index="1DupvO">
        <child id="1144230900587" name="variable" index="1Duv9x" />
      </concept>
      <concept id="1163670490218" name="jetbrains.mps.baseLanguage.structure.SwitchStatement" flags="nn" index="3KaCP$">
        <child id="1163670592366" name="defaultBlock" index="3Kb1Dw" />
        <child id="1163670766145" name="expression" index="3KbGdf" />
        <child id="1163670772911" name="case" index="3KbHQx" />
      </concept>
      <concept id="1163670641947" name="jetbrains.mps.baseLanguage.structure.SwitchCase" flags="ng" index="3KbdKl">
        <child id="1163670677455" name="expression" index="3Kbmr1" />
        <child id="1163670683720" name="body" index="3Kbo56" />
      </concept>
      <concept id="6329021646629104957" name="jetbrains.mps.baseLanguage.structure.TextCommentPart" flags="nn" index="3SKdUq">
        <property id="6329021646629104958" name="text" index="3SKdUp" />
      </concept>
      <concept id="6329021646629104954" name="jetbrains.mps.baseLanguage.structure.SingleLineComment" flags="nn" index="3SKdUt">
        <child id="6329021646629175155" name="commentPart" index="3SKWNk" />
      </concept>
      <concept id="1146644602865" name="jetbrains.mps.baseLanguage.structure.PublicVisibility" flags="nn" index="3Tm1VV" />
      <concept id="1146644623116" name="jetbrains.mps.baseLanguage.structure.PrivateVisibility" flags="nn" index="3Tm6S6" />
    </language>
    <language id="fd392034-7849-419d-9071-12563d152375" name="jetbrains.mps.baseLanguage.closures">
      <concept id="1199569711397" name="jetbrains.mps.baseLanguage.closures.structure.ClosureLiteral" flags="nn" index="1bVj0M">
        <child id="1199569906740" name="parameter" index="1bW2Oz" />
        <child id="1199569916463" name="body" index="1bW5cS" />
      </concept>
    </language>
    <language id="f2801650-65d5-424e-bb1b-463a8781b786" name="jetbrains.mps.baseLanguage.javadoc">
      <concept id="5349172909345501395" name="jetbrains.mps.baseLanguage.javadoc.structure.BaseDocComment" flags="ng" index="P$AiS">
        <child id="8465538089690331502" name="body" index="TZ5H$" />
      </concept>
      <concept id="5349172909345532724" name="jetbrains.mps.baseLanguage.javadoc.structure.MethodDocComment" flags="ng" index="P$JXv" />
      <concept id="8465538089690331500" name="jetbrains.mps.baseLanguage.javadoc.structure.CommentLine" flags="ng" index="TZ5HA">
        <child id="8970989240999019149" name="part" index="1dT_Ay" />
      </concept>
      <concept id="8970989240999019143" name="jetbrains.mps.baseLanguage.javadoc.structure.TextCommentLinePart" flags="ng" index="1dT_AC">
        <property id="8970989240999019144" name="text" index="1dT_AB" />
      </concept>
      <concept id="2068944020170241612" name="jetbrains.mps.baseLanguage.javadoc.structure.ClassifierDocComment" flags="ng" index="3UR2Jj" />
    </language>
    <language id="b83431fe-5c8f-40bc-8a36-65e25f4dd253" name="jetbrains.mps.lang.textGen">
      <concept id="1237305334312" name="jetbrains.mps.lang.textGen.structure.NodeAppendPart" flags="ng" index="l9hG8">
        <child id="1237305790512" name="value" index="lb14g" />
      </concept>
      <concept id="1237305491868" name="jetbrains.mps.lang.textGen.structure.CollectionAppendPart" flags="ng" index="l9S2W">
        <child id="1237305945551" name="list" index="lbANJ" />
      </concept>
      <concept id="1237305557638" name="jetbrains.mps.lang.textGen.structure.ConstantStringAppendPart" flags="ng" index="la8eA" />
      <concept id="1237306079178" name="jetbrains.mps.lang.textGen.structure.AppendOperation" flags="nn" index="lc7rE">
        <child id="1237306115446" name="part" index="lcghm" />
      </concept>
    </language>
    <language id="3a13115c-633c-4c5c-bbcc-75c4219e9555" name="jetbrains.mps.lang.quotation">
      <concept id="1196350785110" name="jetbrains.mps.lang.quotation.structure.AbstractAntiquotation" flags="ng" index="2c44t0">
        <property id="6489343236075007666" name="label" index="3hQQBS" />
        <child id="1196350785111" name="expression" index="2c44t1" />
      </concept>
      <concept id="1196350785117" name="jetbrains.mps.lang.quotation.structure.ReferenceAntiquotation" flags="ng" index="2c44tb" />
      <concept id="1196350785112" name="jetbrains.mps.lang.quotation.structure.Antiquotation" flags="ng" index="2c44te" />
      <concept id="1196350785113" name="jetbrains.mps.lang.quotation.structure.Quotation" flags="nn" index="2c44tf">
        <child id="1196350785114" name="quotedNode" index="2c44tc" />
      </concept>
      <concept id="1196866233735" name="jetbrains.mps.lang.quotation.structure.PropertyAntiquotation" flags="ng" index="2EMmih" />
    </language>
    <language id="7866978e-a0f0-4cc7-81bc-4d213d9375e1" name="jetbrains.mps.lang.smodel">
      <concept id="1179409122411" name="jetbrains.mps.lang.smodel.structure.Node_ConceptMethodCall" flags="nn" index="2qgKlT" />
      <concept id="1138661924179" name="jetbrains.mps.lang.smodel.structure.Property_SetOperation" flags="nn" index="tyxLq">
        <child id="1138662048170" name="value" index="tz02z" />
      </concept>
      <concept id="1143226024141" name="jetbrains.mps.lang.smodel.structure.SModelType" flags="in" index="H_c77" />
      <concept id="1181949435690" name="jetbrains.mps.lang.smodel.structure.Concept_NewInstance" flags="nn" index="LFhST" />
      <concept id="1171323947159" name="jetbrains.mps.lang.smodel.structure.Model_NodesOperation" flags="nn" index="2SmgA7" />
      <concept id="6973815483243445083" name="jetbrains.mps.lang.smodel.structure.EnumMemberValueRefExpression" flags="nn" index="3f7Wdw">
        <reference id="6973815483243565416" name="member" index="3f7u_j" />
        <reference id="6973815483243564601" name="enum" index="3f7vo2" />
      </concept>
      <concept id="1180636770613" name="jetbrains.mps.lang.smodel.structure.SNodeCreator" flags="nn" index="3zrR0B">
        <child id="1180636770616" name="createdType" index="3zrR0E" />
      </concept>
      <concept id="1140137987495" name="jetbrains.mps.lang.smodel.structure.SNodeTypeCastExpression" flags="nn" index="1PxgMI">
        <reference id="1140138128738" name="concept" index="1PxNhF" />
        <child id="1140138123956" name="leftExpression" index="1PxMeX" />
      </concept>
      <concept id="1138055754698" name="jetbrains.mps.lang.smodel.structure.SNodeType" flags="in" index="3Tqbb2">
        <reference id="1138405853777" name="concept" index="ehGHo" />
      </concept>
      <concept id="1138056022639" name="jetbrains.mps.lang.smodel.structure.SPropertyAccess" flags="nn" index="3TrcHB">
        <reference id="1138056395725" name="property" index="3TsBF5" />
      </concept>
      <concept id="1138056143562" name="jetbrains.mps.lang.smodel.structure.SLinkAccess" flags="nn" index="3TrEf2">
        <reference id="1138056516764" name="link" index="3Tt5mk" />
      </concept>
      <concept id="1138056282393" name="jetbrains.mps.lang.smodel.structure.SLinkListAccess" flags="nn" index="3Tsc0h">
        <reference id="1138056546658" name="link" index="3TtcxE" />
      </concept>
    </language>
    <language id="ceab5195-25ea-4f22-9b92-103b95ca8c0c" name="jetbrains.mps.lang.core">
      <concept id="1133920641626" name="jetbrains.mps.lang.core.structure.BaseConcept" flags="ng" index="2VYdi">
        <child id="5169995583184591170" name="smodelAttribute" index="lGtFl" />
      </concept>
      <concept id="3364660638048049750" name="jetbrains.mps.lang.core.structure.PropertyAttribute" flags="ng" index="A9Btg">
        <property id="1757699476691236117" name="propertyName" index="2qtEX9" />
        <property id="1341860900487648621" name="propertyId" index="P4ACc" />
      </concept>
      <concept id="3364660638048049745" name="jetbrains.mps.lang.core.structure.LinkAttribute" flags="ng" index="A9Btn">
        <property id="1757699476691236116" name="linkRole" index="2qtEX8" />
        <property id="1341860900488019036" name="linkId" index="P3scX" />
      </concept>
      <concept id="1169194658468" name="jetbrains.mps.lang.core.structure.INamedConcept" flags="ng" index="TrEIO">
        <property id="1169194664001" name="name" index="TrG5h" />
      </concept>
    </language>
    <language id="83888646-71ce-4f1c-9c53-c54016f6ad4f" name="jetbrains.mps.baseLanguage.collections">
      <concept id="1204796164442" name="jetbrains.mps.baseLanguage.collections.structure.InternalSequenceOperation" flags="nn" index="23sCx2">
        <child id="1204796294226" name="closure" index="23t8la" />
      </concept>
      <concept id="540871147943773365" name="jetbrains.mps.baseLanguage.collections.structure.SingleArgumentSequenceOperation" flags="nn" index="25WWJ4">
        <child id="540871147943773366" name="argument" index="25WWJ7" />
      </concept>
      <concept id="1151688443754" name="jetbrains.mps.baseLanguage.collections.structure.ListType" flags="in" index="_YKpA">
        <child id="1151688676805" name="elementType" index="_ZDj9" />
      </concept>
      <concept id="1151702311717" name="jetbrains.mps.baseLanguage.collections.structure.ToListOperation" flags="nn" index="ANE8D" />
      <concept id="1203518072036" name="jetbrains.mps.baseLanguage.collections.structure.SmartClosureParameterDeclaration" flags="ig" index="Rh6nW" />
      <concept id="1160612413312" name="jetbrains.mps.baseLanguage.collections.structure.AddElementOperation" flags="nn" index="TSZUe" />
      <concept id="1162934736510" name="jetbrains.mps.baseLanguage.collections.structure.GetElementOperation" flags="nn" index="34jXtK" />
      <concept id="1202120902084" name="jetbrains.mps.baseLanguage.collections.structure.WhereOperation" flags="nn" index="3zZkjj" />
      <concept id="1202128969694" name="jetbrains.mps.baseLanguage.collections.structure.SelectOperation" flags="nn" index="3$u5V9" />
    </language>
  </registry>
  <node concept="312cEu" id="Pei6ieTOev">
    <property role="TrG5h" value="EditorHelper" />
    <node concept="2YIFZL" id="4X6IwqdNFOl" role="jymVt">
      <property role="TrG5h" value="createEditor" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="4X6IwqdNFOn" role="3clF47">
        <node concept="3cpWs8" id="4X6IwqdNFOo" role="3cqZAp">
          <node concept="3cpWsn" id="4X6IwqdNFOp" role="3cpWs9">
            <property role="TrG5h" value="nodeDecl" />
            <node concept="3Tqbb2" id="4X6IwqdNFOq" role="1tU5fm">
              <ref role="ehGHo" to="tpce:f_TIwhg" resolve="ConceptDeclaration" />
            </node>
            <node concept="10QFUN" id="4X6IwqdNFOr" role="33vP2m">
              <node concept="3Tqbb2" id="4X6IwqdNFOs" role="10QFUM">
                <ref role="ehGHo" to="tpce:f_TIwhg" resolve="ConceptDeclaration" />
              </node>
              <node concept="37vLTw" id="4X6IwqdNFOt" role="10QFUP">
                <ref role="3cqZAo" node="4X6IwqdNFQ7" resolve="node" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="4X6IwqdNFOu" role="3cqZAp">
          <node concept="3cpWsn" id="4X6IwqdNFOv" role="3cpWs9">
            <property role="TrG5h" value="editor" />
            <node concept="3Tqbb2" id="4X6IwqdNFOw" role="1tU5fm">
              <ref role="ehGHo" to="tpc2:fA4kQeF" resolve="ConceptEditorDeclaration" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="4X6IwqdNFOx" role="3cqZAp" />
        <node concept="3clFbF" id="4X6IwqdNFOy" role="3cqZAp">
          <node concept="37vLTI" id="4X6IwqdNFOz" role="3clFbG">
            <node concept="2c44tf" id="4X6IwqdNFO$" role="37vLTx">
              <node concept="24kQdi" id="4X6IwqdNFO_" role="2c44tc">
                <node concept="2c44tb" id="4X6IwqdNFOA" role="lGtFl">
                  <property role="P3scX" value="18bc6592-03a6-4e29-a83a-7ff23bde13ba/1166049232041/1166049300910" />
                  <property role="2qtEX8" value="conceptDeclaration" />
                  <node concept="37vLTw" id="4X6IwqdNFOB" role="2c44t1">
                    <ref role="3cqZAo" node="4X6IwqdNFOp" resolve="nodeDecl" />
                  </node>
                </node>
                <node concept="3EZMnI" id="4X6IwqdNFOC" role="2wV5jI">
                  <node concept="l2Vlx" id="4X6IwqdNFOD" role="2iSdaV" />
                </node>
              </node>
            </node>
            <node concept="37vLTw" id="4X6IwqdNFOE" role="37vLTJ">
              <ref role="3cqZAo" node="4X6IwqdNFOv" resolve="editor" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="4X6IwqdNFOF" role="3cqZAp" />
        <node concept="3clFbF" id="4X6IwqdNFOG" role="3cqZAp">
          <node concept="37vLTI" id="4X6IwqdNFOH" role="3clFbG">
            <node concept="2OqwBi" id="4X6IwqdNFOI" role="37vLTx">
              <node concept="37vLTw" id="4X6IwqdNFOJ" role="2Oq$k0">
                <ref role="3cqZAo" node="4X6IwqdNFOp" resolve="nodeDecl" />
              </node>
              <node concept="3TrcHB" id="4X6IwqdNFOK" role="2OqNvi">
                <ref role="3TsBF5" to="tpck:hnGE5uv" resolve="virtualPackage" />
              </node>
            </node>
            <node concept="2OqwBi" id="4X6IwqdNFOL" role="37vLTJ">
              <node concept="37vLTw" id="4X6IwqdNFOM" role="2Oq$k0">
                <ref role="3cqZAo" node="4X6IwqdNFOv" resolve="editor" />
              </node>
              <node concept="3TrcHB" id="4X6IwqdNFON" role="2OqNvi">
                <ref role="3TsBF5" to="tpck:hnGE5uv" resolve="virtualPackage" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="4X6IwqdNFOO" role="3cqZAp" />
        <node concept="3cpWs8" id="4X6IwqdNFOP" role="3cqZAp">
          <node concept="3cpWsn" id="4X6IwqdNFOQ" role="3cpWs9">
            <property role="TrG5h" value="cellModel" />
            <node concept="3Tqbb2" id="4X6IwqdNFOR" role="1tU5fm">
              <ref role="ehGHo" to="tpc2:fBEZMkn" resolve="CellModel_Collection" />
            </node>
            <node concept="10QFUN" id="4X6IwqdNFOS" role="33vP2m">
              <node concept="3Tqbb2" id="4X6IwqdNFOT" role="10QFUM">
                <ref role="ehGHo" to="tpc2:fBEZMkn" resolve="CellModel_Collection" />
              </node>
              <node concept="2OqwBi" id="4X6IwqdNFOU" role="10QFUP">
                <node concept="37vLTw" id="4X6IwqdNFOV" role="2Oq$k0">
                  <ref role="3cqZAo" node="4X6IwqdNFOv" resolve="editor" />
                </node>
                <node concept="3TrEf2" id="4X6IwqdNFOW" role="2OqNvi">
                  <ref role="3Tt5mk" to="tpc2:fIwV5gl" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="4X6IwqdOuq8" role="3cqZAp" />
        <node concept="3cpWs8" id="4X6IwqdOw_P" role="3cqZAp">
          <node concept="3cpWsn" id="4X6IwqdOw_Q" role="3cpWs9">
            <property role="TrG5h" value="propertyIndex" />
            <node concept="10Oyi0" id="4X6IwqdOw_R" role="1tU5fm" />
            <node concept="3cmrfG" id="4X6IwqdOw_S" role="33vP2m">
              <property role="3cmrfH" value="0" />
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="4X6IwqdOvPh" role="3cqZAp">
          <node concept="3cpWsn" id="4X6IwqdOvPk" role="3cpWs9">
            <property role="TrG5h" value="childIndex" />
            <node concept="10Oyi0" id="4X6IwqdOvPf" role="1tU5fm" />
            <node concept="3cmrfG" id="4X6IwqdOw_v" role="33vP2m">
              <property role="3cmrfH" value="0" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="4X6IwqdObtF" role="3cqZAp" />
        <node concept="1DcWWT" id="4X6IwqdNFOY" role="3cqZAp">
          <node concept="3clFbS" id="4X6IwqdNFOZ" role="2LFqv$">
            <node concept="3cpWs8" id="4X6IwqdNFP0" role="3cqZAp">
              <node concept="3cpWsn" id="4X6IwqdNFP1" role="3cpWs9">
                <property role="TrG5h" value="r" />
                <node concept="3uibUv" id="Pei6ieUdoc" role="1tU5fm">
                  <ref role="3uigEE" to="kr2q:~Rule" resolve="Rule" />
                </node>
                <node concept="2OqwBi" id="4X6IwqdNFP3" role="33vP2m">
                  <node concept="37vLTw" id="4X6IwqdNFP4" role="2Oq$k0">
                    <ref role="3cqZAo" node="4X6IwqdNFQ0" resolve="reference" />
                  </node>
                  <node concept="2OwXpG" id="Pei6ieUdqt" role="2OqNvi">
                    <ref role="2Oxat5" to="kr2q:~RuleReference.rule" resolve="rule" />
                  </node>
                </node>
              </node>
            </node>
            <node concept="3clFbH" id="4X6IwqdNFP6" role="3cqZAp" />
            <node concept="3clFbJ" id="4X6IwqdNFP7" role="3cqZAp">
              <node concept="3clFbS" id="4X6IwqdNFP8" role="3clFbx">
                <node concept="3cpWs8" id="4X6IwqdNIPa" role="3cqZAp">
                  <node concept="3cpWsn" id="4X6IwqdNIPd" role="3cpWs9">
                    <property role="TrG5h" value="cell" />
                    <node concept="2c44tf" id="4X6IwqdNFPf" role="33vP2m">
                      <node concept="3F0ifn" id="4X6IwqdNFPg" role="2c44tc">
                        <property role="3F0ifm" value="" />
                        <node concept="2EMmih" id="4X6IwqdNFPh" role="lGtFl">
                          <property role="P4ACc" value="18bc6592-03a6-4e29-a83a-7ff23bde13ba/1073389577006/1073389577007" />
                          <property role="2qtEX9" value="text" />
                          <property role="3hQQBS" value="CellModel_Constant" />
                          <node concept="2OqwBi" id="4X6IwqdNFPi" role="2c44t1">
                            <node concept="1eOMI4" id="4X6IwqdNFPj" role="2Oq$k0">
                              <node concept="10QFUN" id="4X6IwqdNFPk" role="1eOMHV">
                                <node concept="3uibUv" id="Pei6ieUdph" role="10QFUM">
                                  <ref role="3uigEE" to="kr2q:~LiteralRule" resolve="LiteralRule" />
                                </node>
                                <node concept="37vLTw" id="4X6IwqdNFPm" role="10QFUP">
                                  <ref role="3cqZAo" node="4X6IwqdNFP1" resolve="r" />
                                </node>
                              </node>
                            </node>
                            <node concept="2OwXpG" id="Pei6ieUdte" role="2OqNvi">
                              <ref role="2Oxat5" to="kr2q:~LiteralRule.value" resolve="value" />
                            </node>
                          </node>
                        </node>
                      </node>
                    </node>
                    <node concept="3Tqbb2" id="4X6IwqdNJdN" role="1tU5fm">
                      <ref role="ehGHo" to="tpc2:fBF0icI" resolve="CellModel_Constant" />
                    </node>
                  </node>
                </node>
                <node concept="3clFbF" id="4X6IwqdNFP9" role="3cqZAp">
                  <node concept="2OqwBi" id="4X6IwqdNFPa" role="3clFbG">
                    <node concept="2OqwBi" id="4X6IwqdNFPb" role="2Oq$k0">
                      <node concept="37vLTw" id="4X6IwqdNFPc" role="2Oq$k0">
                        <ref role="3cqZAo" node="4X6IwqdNFOQ" resolve="cellModel" />
                      </node>
                      <node concept="3Tsc0h" id="4X6IwqdNFPd" role="2OqNvi">
                        <ref role="3TtcxE" to="tpc2:fBEZMko" />
                      </node>
                    </node>
                    <node concept="TSZUe" id="4X6IwqdNFPe" role="2OqNvi">
                      <node concept="37vLTw" id="4X6IwqdNJfO" role="25WWJ7">
                        <ref role="3cqZAo" node="4X6IwqdNIPd" resolve="cell" />
                      </node>
                    </node>
                  </node>
                </node>
                <node concept="3clFbH" id="4ELXOTA_gEU" role="3cqZAp" />
              </node>
              <node concept="2ZW3vV" id="4X6IwqdNFPo" role="3clFbw">
                <node concept="3uibUv" id="Pei6ieUd6r" role="2ZW6by">
                  <ref role="3uigEE" to="kr2q:~LiteralRule" resolve="LiteralRule" />
                </node>
                <node concept="37vLTw" id="4X6IwqdNFPq" role="2ZW6bz">
                  <ref role="3cqZAo" node="4X6IwqdNFP1" resolve="r" />
                </node>
              </node>
              <node concept="3eNFk2" id="4X6IwqdNFPr" role="3eNLev">
                <node concept="2ZW3vV" id="4X6IwqdNFPs" role="3eO9$A">
                  <node concept="3uibUv" id="Pei6ieUdnM" role="2ZW6by">
                    <ref role="3uigEE" to="kr2q:~RegexRule" resolve="RegexRule" />
                  </node>
                  <node concept="37vLTw" id="4X6IwqdNFPu" role="2ZW6bz">
                    <ref role="3cqZAo" node="4X6IwqdNFP1" resolve="r" />
                  </node>
                </node>
                <node concept="3clFbS" id="4X6IwqdNFPv" role="3eOfB_">
                  <node concept="3cpWs8" id="4X6IwqdO6fk" role="3cqZAp">
                    <node concept="3cpWsn" id="4X6IwqdO6fl" role="3cpWs9">
                      <property role="TrG5h" value="property" />
                      <node concept="3Tqbb2" id="4X6IwqdOmQ2" role="1tU5fm">
                        <ref role="ehGHo" to="tpce:f_TJgxF" resolve="PropertyDeclaration" />
                      </node>
                      <node concept="2YIFZM" id="Pei6ieUdkN" role="33vP2m">
                        <ref role="37wK5l" node="3pfFnS02Gte" resolve="getProperty" />
                        <ref role="1Pybhc" node="Pei6ieTS8A" resolve="NodeHelper" />
                        <node concept="37vLTw" id="Pei6ieUdkO" role="37wK5m">
                          <ref role="3cqZAo" node="4X6IwqdNFOp" resolve="nodeDecl" />
                        </node>
                        <node concept="3uNrnE" id="Pei6ieUdkP" role="37wK5m">
                          <node concept="37vLTw" id="Pei6ieUdkQ" role="2$L3a6">
                            <ref role="3cqZAo" node="4X6IwqdOw_Q" resolve="propertyIndex" />
                          </node>
                        </node>
                      </node>
                    </node>
                  </node>
                  <node concept="3cpWs8" id="4X6IwqdNJnW" role="3cqZAp">
                    <node concept="3cpWsn" id="4X6IwqdNJo2" role="3cpWs9">
                      <property role="TrG5h" value="cell" />
                      <node concept="3Tqbb2" id="4X6IwqdNJou" role="1tU5fm">
                        <ref role="ehGHo" to="tpc2:fBF0A4I" resolve="CellModel_Property" />
                      </node>
                      <node concept="2c44tf" id="4X6IwqdNJrt" role="33vP2m">
                        <node concept="3F0A7n" id="4X6IwqdNJZW" role="2c44tc">
                          <node concept="2c44tb" id="4X6IwqdNKb0" role="lGtFl">
                            <property role="P3scX" value="18bc6592-03a6-4e29-a83a-7ff23bde13ba/1139848536355/1140103550593" />
                            <property role="2qtEX8" value="relationDeclaration" />
                            <node concept="37vLTw" id="4X6IwqdO6sn" role="2c44t1">
                              <ref role="3cqZAo" node="4X6IwqdO6fl" resolve="property" />
                            </node>
                          </node>
                        </node>
                      </node>
                    </node>
                  </node>
                  <node concept="3clFbF" id="4X6IwqdNFPS" role="3cqZAp">
                    <node concept="2OqwBi" id="4X6IwqdNFPT" role="3clFbG">
                      <node concept="2OqwBi" id="4X6IwqdNFPU" role="2Oq$k0">
                        <node concept="37vLTw" id="4X6IwqdNFPV" role="2Oq$k0">
                          <ref role="3cqZAo" node="4X6IwqdNFOQ" resolve="cellModel" />
                        </node>
                        <node concept="3Tsc0h" id="4X6IwqdNFPW" role="2OqNvi">
                          <ref role="3TtcxE" to="tpc2:fBEZMko" />
                        </node>
                      </node>
                      <node concept="TSZUe" id="4X6IwqdNFPX" role="2OqNvi">
                        <node concept="37vLTw" id="4X6IwqdNJSW" role="25WWJ7">
                          <ref role="3cqZAo" node="4X6IwqdNJo2" resolve="cell" />
                        </node>
                      </node>
                    </node>
                  </node>
                  <node concept="3clFbH" id="4ELXOTA_gFG" role="3cqZAp" />
                </node>
              </node>
              <node concept="3eNFk2" id="4X6IwqdOE_X" role="3eNLev">
                <node concept="2ZW3vV" id="4X6IwqdOEGP" role="3eO9$A">
                  <node concept="3uibUv" id="Pei6ieUggm" role="2ZW6by">
                    <ref role="3uigEE" to="kr2q:~ParserRule" resolve="ParserRule" />
                  </node>
                  <node concept="37vLTw" id="4X6IwqdOEFd" role="2ZW6bz">
                    <ref role="3cqZAo" node="4X6IwqdNFP1" resolve="r" />
                  </node>
                </node>
                <node concept="3clFbS" id="4X6IwqdOE_Z" role="3eOfB_">
                  <node concept="3cpWs8" id="4ELXOTA$LDb" role="3cqZAp">
                    <node concept="3cpWsn" id="4ELXOTA$LDh" role="3cpWs9">
                      <property role="TrG5h" value="link" />
                      <node concept="3Tqbb2" id="4ELXOTA$LDS" role="1tU5fm">
                        <ref role="ehGHo" to="tpce:f_TJgxE" resolve="LinkDeclaration" />
                      </node>
                      <node concept="2YIFZM" id="Pei6ieUd8D" role="33vP2m">
                        <ref role="37wK5l" node="4X6IwqdOs4G" resolve="getChildLink" />
                        <ref role="1Pybhc" node="Pei6ieTS8A" resolve="NodeHelper" />
                        <node concept="37vLTw" id="Pei6ieUd8E" role="37wK5m">
                          <ref role="3cqZAo" node="4X6IwqdNFOp" resolve="nodeDecl" />
                        </node>
                        <node concept="3uNrnE" id="Pei6ieUd8F" role="37wK5m">
                          <node concept="37vLTw" id="Pei6ieUd8G" role="2$L3a6">
                            <ref role="3cqZAo" node="4X6IwqdOvPk" resolve="childIndex" />
                          </node>
                        </node>
                      </node>
                    </node>
                  </node>
                  <node concept="3clFbH" id="5TDF6Q7k1Wn" role="3cqZAp" />
                  <node concept="3cpWs8" id="5TDF6Q7k2a1" role="3cqZAp">
                    <node concept="3cpWsn" id="5TDF6Q7k2a7" role="3cpWs9">
                      <property role="TrG5h" value="cell" />
                      <node concept="3Tqbb2" id="5TDF6Q7k2e$" role="1tU5fm">
                        <ref role="ehGHo" to="tpc2:g_$h64z" resolve="CellModel_WithRole" />
                      </node>
                    </node>
                  </node>
                  <node concept="3clFbH" id="5TDF6Q7k250" role="3cqZAp" />
                  <node concept="3clFbJ" id="5TDF6Q7k2nc" role="3cqZAp">
                    <node concept="3clFbS" id="5TDF6Q7k2ne" role="3clFbx">
                      <node concept="3cpWs8" id="5TDF6Q7k5TL" role="3cqZAp">
                        <node concept="3cpWsn" id="5TDF6Q7k5TR" role="3cpWs9">
                          <property role="TrG5h" value="c" />
                          <node concept="3Tqbb2" id="5TDF6Q7k5Uy" role="1tU5fm">
                            <ref role="ehGHo" to="tpc2:fBF1sR7" resolve="CellModel_RefNode" />
                          </node>
                          <node concept="2c44tf" id="4ELXOTA$Ly8" role="33vP2m">
                            <node concept="3F1sOY" id="4ELXOTA$LBG" role="2c44tc">
                              <node concept="2c44tb" id="4ELXOTA$QNM" role="lGtFl">
                                <property role="P3scX" value="18bc6592-03a6-4e29-a83a-7ff23bde13ba/1139848536355/1140103550593" />
                                <property role="2qtEX8" value="relationDeclaration" />
                                <node concept="37vLTw" id="4ELXOTA$QPG" role="2c44t1">
                                  <ref role="3cqZAo" node="4ELXOTA$LDh" resolve="link" />
                                </node>
                              </node>
                            </node>
                          </node>
                        </node>
                      </node>
                      <node concept="3clFbF" id="5TDF6Q7k5Z8" role="3cqZAp">
                        <node concept="37vLTI" id="5TDF6Q7k6qt" role="3clFbG">
                          <node concept="3clFbT" id="5TDF6Q7k6s0" role="37vLTx">
                            <property role="3clFbU" value="true" />
                          </node>
                          <node concept="2OqwBi" id="5TDF6Q7k62M" role="37vLTJ">
                            <node concept="37vLTw" id="5TDF6Q7k5Z6" role="2Oq$k0">
                              <ref role="3cqZAo" node="5TDF6Q7k5TR" resolve="c" />
                            </node>
                            <node concept="3TrcHB" id="5TDF6Q7k6oz" role="2OqNvi">
                              <ref role="3TsBF5" to="tpc2:Ujlmrg18k" resolve="customizeEmptyCell" />
                            </node>
                          </node>
                        </node>
                      </node>
                      <node concept="3clFbF" id="5TDF6Q7k6ud" role="3cqZAp">
                        <node concept="37vLTI" id="5TDF6Q7k6Yn" role="3clFbG">
                          <node concept="2OqwBi" id="5TDF6Q7k6AM" role="37vLTJ">
                            <node concept="37vLTw" id="5TDF6Q7k6ub" role="2Oq$k0">
                              <ref role="3cqZAo" node="5TDF6Q7k5TR" resolve="c" />
                            </node>
                            <node concept="3TrEf2" id="5TDF6Q7k6Wl" role="2OqNvi">
                              <ref role="3Tt5mk" to="tpc2:UjlmrgInb" />
                            </node>
                          </node>
                          <node concept="2c44tf" id="4ELXOTA_D0P" role="37vLTx">
                            <node concept="3F0ifn" id="4ELXOTA_D0Q" role="2c44tc">
                              <property role="3F0ifm" value="" />
                              <node concept="2EMmih" id="4ELXOTA_D0R" role="lGtFl">
                                <property role="P4ACc" value="18bc6592-03a6-4e29-a83a-7ff23bde13ba/1073389577006/1073389577007" />
                                <property role="2qtEX9" value="text" />
                                <property role="3hQQBS" value="CellModel_Constant" />
                                <node concept="Xl_RD" id="4ELXOTA_NyA" role="2c44t1">
                                  <property role="Xl_RC" value=" " />
                                </node>
                              </node>
                            </node>
                          </node>
                        </node>
                      </node>
                      <node concept="3clFbF" id="5TDF6Q7k4Ub" role="3cqZAp">
                        <node concept="37vLTI" id="5TDF6Q7k4WR" role="3clFbG">
                          <node concept="37vLTw" id="5TDF6Q7k4Ua" role="37vLTJ">
                            <ref role="3cqZAo" node="5TDF6Q7k2a7" resolve="cell" />
                          </node>
                          <node concept="37vLTw" id="5TDF6Q7k74o" role="37vLTx">
                            <ref role="3cqZAo" node="5TDF6Q7k5TR" resolve="c" />
                          </node>
                        </node>
                      </node>
                    </node>
                    <node concept="3clFbC" id="5TDF6Q7k4wE" role="3clFbw">
                      <node concept="Rm8GO" id="Pei6ieUDxI" role="3uHU7w">
                        <ref role="Rm8GQ" to="kr2q:~Quantity.EXACTLY_ONE" resolve="EXACTLY_ONE" />
                        <ref role="1Px2BO" to="kr2q:~Quantity" resolve="Quantity" />
                      </node>
                      <node concept="2OqwBi" id="5TDF6Q7k4q7" role="3uHU7B">
                        <node concept="37vLTw" id="5TDF6Q7k4ou" role="2Oq$k0">
                          <ref role="3cqZAo" node="4X6IwqdNFQ0" resolve="reference" />
                        </node>
                        <node concept="2OwXpG" id="Pei6ieUghC" role="2OqNvi">
                          <ref role="2Oxat5" to="kr2q:~RuleReference.quantity" resolve="quantity" />
                        </node>
                      </node>
                    </node>
                    <node concept="9aQIb" id="5TDF6Q7k4Zw" role="9aQIa">
                      <node concept="3clFbS" id="5TDF6Q7k4Zx" role="9aQI4">
                        <node concept="3cpWs8" id="5TDF6Q7k78V" role="3cqZAp">
                          <node concept="3cpWsn" id="5TDF6Q7k791" role="3cpWs9">
                            <property role="TrG5h" value="c" />
                            <node concept="3Tqbb2" id="5TDF6Q7k79G" role="1tU5fm">
                              <ref role="ehGHo" to="tpc2:fBF2Hee" resolve="CellModel_RefNodeList" />
                            </node>
                            <node concept="2c44tf" id="5TDF6Q7jWwr" role="33vP2m">
                              <node concept="3F2HdR" id="5TDF6Q7jWBd" role="2c44tc">
                                <node concept="3F0ifn" id="5TDF6Q7jX1v" role="2czzBI">
                                  <property role="3F0ifm" value=" " />
                                </node>
                                <node concept="2c44tb" id="5TDF6Q7jX27" role="lGtFl">
                                  <property role="P3scX" value="18bc6592-03a6-4e29-a83a-7ff23bde13ba/1139848536355/1140103550593" />
                                  <property role="2qtEX8" value="relationDeclaration" />
                                  <node concept="37vLTw" id="5TDF6Q7jX4h" role="2c44t1">
                                    <ref role="3cqZAo" node="4ELXOTA$LDh" resolve="link" />
                                  </node>
                                </node>
                              </node>
                            </node>
                          </node>
                        </node>
                        <node concept="3clFbF" id="5TDF6Q7k7gE" role="3cqZAp">
                          <node concept="37vLTI" id="5TDF6Q7k7OO" role="3clFbG">
                            <node concept="2OqwBi" id="5TDF6Q7k7AD" role="37vLTJ">
                              <node concept="37vLTw" id="5TDF6Q7k7yH" role="2Oq$k0">
                                <ref role="3cqZAo" node="5TDF6Q7k791" resolve="c" />
                              </node>
                              <node concept="3TrEf2" id="5TDF6Q7k7MM" role="2OqNvi">
                                <ref role="3Tt5mk" to="tpc2:gAczzzB" />
                              </node>
                            </node>
                            <node concept="2c44tf" id="5TDF6Q7k7QO" role="37vLTx">
                              <node concept="3F0ifn" id="5TDF6Q7k7QP" role="2c44tc">
                                <property role="3F0ifm" value="" />
                                <node concept="2EMmih" id="5TDF6Q7k7QQ" role="lGtFl">
                                  <property role="P4ACc" value="18bc6592-03a6-4e29-a83a-7ff23bde13ba/1073389577006/1073389577007" />
                                  <property role="2qtEX9" value="text" />
                                  <property role="3hQQBS" value="CellModel_Constant" />
                                  <node concept="Xl_RD" id="5TDF6Q7k7QR" role="2c44t1">
                                    <property role="Xl_RC" value=" " />
                                  </node>
                                </node>
                              </node>
                            </node>
                          </node>
                        </node>
                        <node concept="3clFbF" id="5TDF6Q7k51v" role="3cqZAp">
                          <node concept="37vLTI" id="5TDF6Q7k54J" role="3clFbG">
                            <node concept="37vLTw" id="5TDF6Q7k51u" role="37vLTJ">
                              <ref role="3cqZAo" node="5TDF6Q7k2a7" resolve="cell" />
                            </node>
                            <node concept="37vLTw" id="5TDF6Q7k7eb" role="37vLTx">
                              <ref role="3cqZAo" node="5TDF6Q7k791" resolve="c" />
                            </node>
                          </node>
                        </node>
                      </node>
                    </node>
                  </node>
                  <node concept="3clFbH" id="5TDF6Q7k2i6" role="3cqZAp" />
                  <node concept="3clFbF" id="4ELXOTA_dvo" role="3cqZAp">
                    <node concept="2OqwBi" id="4ELXOTA_epP" role="3clFbG">
                      <node concept="2OqwBi" id="4ELXOTA_dAV" role="2Oq$k0">
                        <node concept="37vLTw" id="4ELXOTA_dvm" role="2Oq$k0">
                          <ref role="3cqZAo" node="4X6IwqdNFOQ" resolve="cellModel" />
                        </node>
                        <node concept="3Tsc0h" id="4ELXOTA_dUf" role="2OqNvi">
                          <ref role="3TtcxE" to="tpc2:fBEZMko" />
                        </node>
                      </node>
                      <node concept="TSZUe" id="4ELXOTA_gsG" role="2OqNvi">
                        <node concept="37vLTw" id="5TDF6Q7k7Yd" role="25WWJ7">
                          <ref role="3cqZAo" node="5TDF6Q7k2a7" resolve="cell" />
                        </node>
                      </node>
                    </node>
                  </node>
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWsn" id="4X6IwqdNFQ0" role="1Duv9x">
            <property role="TrG5h" value="reference" />
            <node concept="3uibUv" id="Pei6ieUcWN" role="1tU5fm">
              <ref role="3uigEE" to="kr2q:~RuleReference" resolve="RuleReference" />
            </node>
          </node>
          <node concept="37vLTw" id="4X6IwqdNFQ2" role="1DdaDG">
            <ref role="3cqZAo" node="4X6IwqdNFQ9" resolve="children" />
          </node>
        </node>
        <node concept="3clFbH" id="4X6IwqdNFQ3" role="3cqZAp" />
        <node concept="3cpWs6" id="4X6IwqdNFQ4" role="3cqZAp">
          <node concept="37vLTw" id="4X6IwqdNFQ5" role="3cqZAk">
            <ref role="3cqZAo" node="4X6IwqdNFOv" resolve="editor" />
          </node>
        </node>
      </node>
      <node concept="3uibUv" id="4X6IwqdNFQ6" role="3clF45">
        <ref role="3uigEE" to="mhbf:~SNode" resolve="SNode" />
      </node>
      <node concept="37vLTG" id="4X6IwqdNFQ7" role="3clF46">
        <property role="TrG5h" value="node" />
        <node concept="3uibUv" id="4X6IwqdNFQ8" role="1tU5fm">
          <ref role="3uigEE" to="mhbf:~SNode" resolve="SNode" />
        </node>
      </node>
      <node concept="37vLTG" id="4X6IwqdNFQ9" role="3clF46">
        <property role="TrG5h" value="children" />
        <node concept="_YKpA" id="4X6IwqdNFQa" role="1tU5fm">
          <node concept="3uibUv" id="Pei6ieUcO9" role="_ZDj9">
            <ref role="3uigEE" to="kr2q:~RuleReference" resolve="RuleReference" />
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="4X6IwqdNFQc" role="1B3o_S" />
    </node>
    <node concept="3Tm1VV" id="Pei6ieTOew" role="1B3o_S" />
    <node concept="3UR2Jj" id="Pei6ieTQTq" role="lGtFl">
      <node concept="TZ5HA" id="Pei6ieTQTr" role="TZ5H$">
        <node concept="1dT_AC" id="Pei6ieTQTs" role="1dT_Ay">
          <property role="1dT_AB" value="Helper class for creating projectional editors." />
        </node>
      </node>
    </node>
  </node>
  <node concept="312cEu" id="Pei6ieTQTz">
    <property role="TrG5h" value="NodeFactory" />
    <node concept="312cEg" id="4X6IwqdNjpw" role="jymVt">
      <property role="34CwA1" value="false" />
      <property role="eg7rD" value="false" />
      <property role="TrG5h" value="structureModel" />
      <property role="3TUv4t" value="false" />
      <node concept="3Tm6S6" id="4X6IwqdNjl4" role="1B3o_S" />
      <node concept="H_c77" id="4X6IwqdNjpt" role="1tU5fm" />
    </node>
    <node concept="2tJIrI" id="4X6IwqdNjtW" role="jymVt" />
    <node concept="3clFbW" id="4X6IwqdNjFl" role="jymVt">
      <node concept="3cqZAl" id="4X6IwqdNjFm" role="3clF45" />
      <node concept="3clFbS" id="4X6IwqdNjFo" role="3clF47">
        <node concept="3clFbF" id="4X6IwqdNjKM" role="3cqZAp">
          <node concept="37vLTI" id="4X6IwqdNjPh" role="3clFbG">
            <node concept="37vLTw" id="4X6IwqdNjRf" role="37vLTx">
              <ref role="3cqZAo" node="4X6IwqdNjJW" resolve="structureModel" />
            </node>
            <node concept="2OqwBi" id="4X6IwqdNjLa" role="37vLTJ">
              <node concept="Xjq3P" id="4X6IwqdNjKL" role="2Oq$k0" />
              <node concept="2OwXpG" id="4X6IwqdNjOu" role="2OqNvi">
                <ref role="2Oxat5" node="4X6IwqdNjpw" resolve="structureModel" />
              </node>
            </node>
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="4X6IwqdNjAO" role="1B3o_S" />
      <node concept="37vLTG" id="4X6IwqdNjJW" role="3clF46">
        <property role="TrG5h" value="structureModel" />
        <node concept="H_c77" id="4X6IwqdNjJV" role="1tU5fm" />
      </node>
    </node>
    <node concept="2tJIrI" id="4X6IwqdNjgJ" role="jymVt" />
    <node concept="3clFb_" id="4X6IwqdNjRK" role="jymVt">
      <property role="TrG5h" value="createConcept" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="4X6IwqdNjRM" role="3clF47">
        <node concept="3cpWs8" id="4X6IwqdNjRN" role="3cqZAp">
          <node concept="3cpWsn" id="4X6IwqdNjRO" role="3cpWs9">
            <property role="TrG5h" value="node" />
            <node concept="3Tqbb2" id="4X6IwqdNjRP" role="1tU5fm">
              <ref role="ehGHo" to="tpce:f_TIwhg" resolve="ConceptDeclaration" />
            </node>
            <node concept="2ShNRf" id="4X6IwqdNjRQ" role="33vP2m">
              <node concept="3zrR0B" id="4X6IwqdNjRR" role="2ShVmc">
                <node concept="3Tqbb2" id="4X6IwqdNjRS" role="3zrR0E">
                  <ref role="ehGHo" to="tpce:f_TIwhg" resolve="ConceptDeclaration" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="4X6IwqdNjRT" role="3cqZAp" />
        <node concept="3clFbF" id="4X6IwqdNjRU" role="3cqZAp">
          <node concept="37vLTI" id="4X6IwqdNjRV" role="3clFbG">
            <node concept="2OqwBi" id="4X6IwqdNjRW" role="37vLTJ">
              <node concept="37vLTw" id="4X6IwqdNjRX" role="2Oq$k0">
                <ref role="3cqZAo" node="4X6IwqdNjRO" resolve="node" />
              </node>
              <node concept="3TrcHB" id="4X6IwqdNjRY" role="2OqNvi">
                <ref role="3TsBF5" to="tpck:h0TrG11" resolve="name" />
              </node>
            </node>
            <node concept="37vLTw" id="4X6IwqdNjRZ" role="37vLTx">
              <ref role="3cqZAo" node="4X6IwqdNjSv" resolve="name" />
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="4X6IwqdNjS0" role="3cqZAp">
          <node concept="37vLTI" id="4X6IwqdNjS1" role="3clFbG">
            <node concept="37vLTw" id="4X6IwqdNjS2" role="37vLTx">
              <ref role="3cqZAo" node="4X6IwqdNjSx" resolve="alias" />
            </node>
            <node concept="2OqwBi" id="4X6IwqdNjS3" role="37vLTJ">
              <node concept="37vLTw" id="4X6IwqdNjS4" role="2Oq$k0">
                <ref role="3cqZAo" node="4X6IwqdNjRO" resolve="node" />
              </node>
              <node concept="3TrcHB" id="4X6IwqdNjS5" role="2OqNvi">
                <ref role="3TsBF5" to="tpck:gOOYy9I" resolve="alias" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="4X6IwqdNjS6" role="3cqZAp">
          <node concept="37vLTI" id="4X6IwqdNjS7" role="3clFbG">
            <node concept="37vLTw" id="4X6IwqdNjS8" role="37vLTx">
              <ref role="3cqZAo" node="4X6IwqdNjSz" resolve="virtualPackage" />
            </node>
            <node concept="2OqwBi" id="4X6IwqdNjS9" role="37vLTJ">
              <node concept="37vLTw" id="4X6IwqdNjSa" role="2Oq$k0">
                <ref role="3cqZAo" node="4X6IwqdNjRO" resolve="node" />
              </node>
              <node concept="3TrcHB" id="4X6IwqdNjSb" role="2OqNvi">
                <ref role="3TsBF5" to="tpck:hnGE5uv" resolve="virtualPackage" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="4X6IwqdNjSc" role="3cqZAp">
          <node concept="37vLTI" id="4X6IwqdNjSd" role="3clFbG">
            <node concept="2YIFZM" id="4X6IwqdNjSe" role="37vLTx">
              <ref role="1Pybhc" to="twe9:1o9qxtfbpaa" resolve="ConceptIdUtil" />
              <ref role="37wK5l" to="twe9:1o9qxtfbpgW" resolve="generate" />
              <node concept="37vLTw" id="4X6IwqdNjSf" role="37wK5m">
                <ref role="3cqZAo" node="4X6IwqdNjRO" resolve="node" />
              </node>
              <node concept="2OqwBi" id="4X6IwqdNk47" role="37wK5m">
                <node concept="Xjq3P" id="4X6IwqdNk2p" role="2Oq$k0" />
                <node concept="2OwXpG" id="4X6IwqdNk88" role="2OqNvi">
                  <ref role="2Oxat5" node="4X6IwqdNjpw" resolve="structureModel" />
                </node>
              </node>
            </node>
            <node concept="2OqwBi" id="4X6IwqdNjSh" role="37vLTJ">
              <node concept="37vLTw" id="4X6IwqdNjSi" role="2Oq$k0">
                <ref role="3cqZAo" node="4X6IwqdNjRO" resolve="node" />
              </node>
              <node concept="3TrcHB" id="4X6IwqdNjSj" role="2OqNvi">
                <ref role="3TsBF5" to="tpce:1o9qxtf9pgp" resolve="conceptId" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="4X6IwqdNjSk" role="3cqZAp">
          <node concept="37vLTI" id="4X6IwqdNjSl" role="3clFbG">
            <node concept="37vLTw" id="4X6IwqdNjSm" role="37vLTx">
              <ref role="3cqZAo" node="4X6IwqdNjS_" resolve="rootable" />
            </node>
            <node concept="2OqwBi" id="4X6IwqdNjSn" role="37vLTJ">
              <node concept="37vLTw" id="4X6IwqdNjSo" role="2Oq$k0">
                <ref role="3cqZAo" node="4X6IwqdNjRO" resolve="node" />
              </node>
              <node concept="3TrcHB" id="4X6IwqdNjSp" role="2OqNvi">
                <ref role="3TsBF5" to="tpce:fX9Ktp8" resolve="rootable" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="4X6IwqdNjSq" role="3cqZAp" />
        <node concept="3cpWs6" id="4X6IwqdNjSr" role="3cqZAp">
          <node concept="37vLTw" id="4X6IwqdNjSs" role="3cqZAk">
            <ref role="3cqZAo" node="4X6IwqdNjRO" resolve="node" />
          </node>
        </node>
      </node>
      <node concept="3uibUv" id="4X6IwqdNjSu" role="3clF45">
        <ref role="3uigEE" to="mhbf:~SNode" resolve="SNode" />
      </node>
      <node concept="37vLTG" id="4X6IwqdNjSv" role="3clF46">
        <property role="TrG5h" value="name" />
        <node concept="17QB3L" id="4X6IwqdNjSw" role="1tU5fm" />
      </node>
      <node concept="37vLTG" id="4X6IwqdNjSx" role="3clF46">
        <property role="TrG5h" value="alias" />
        <node concept="17QB3L" id="4X6IwqdNjSy" role="1tU5fm" />
      </node>
      <node concept="37vLTG" id="4X6IwqdNjSz" role="3clF46">
        <property role="TrG5h" value="virtualPackage" />
        <node concept="17QB3L" id="4X6IwqdNjS$" role="1tU5fm" />
      </node>
      <node concept="37vLTG" id="4X6IwqdNjS_" role="3clF46">
        <property role="TrG5h" value="rootable" />
        <node concept="10P_77" id="4X6IwqdNjSA" role="1tU5fm" />
      </node>
      <node concept="3Tm1VV" id="4X6IwqdNjSt" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="5Jh2F9ezaZb" role="jymVt" />
    <node concept="3clFb_" id="4X6IwqdNkjw" role="jymVt">
      <property role="TrG5h" value="createInterface" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="4X6IwqdNkjy" role="3clF47">
        <node concept="3cpWs8" id="4X6IwqdNkjz" role="3cqZAp">
          <node concept="3cpWsn" id="4X6IwqdNkj$" role="3cpWs9">
            <property role="TrG5h" value="node" />
            <node concept="3Tqbb2" id="4X6IwqdNkj_" role="1tU5fm">
              <ref role="ehGHo" to="tpce:h0PlHMJ" resolve="InterfaceConceptDeclaration" />
            </node>
            <node concept="2ShNRf" id="4X6IwqdNkjA" role="33vP2m">
              <node concept="3zrR0B" id="4X6IwqdNkjB" role="2ShVmc">
                <node concept="3Tqbb2" id="4X6IwqdNkjC" role="3zrR0E">
                  <ref role="ehGHo" to="tpce:h0PlHMJ" resolve="InterfaceConceptDeclaration" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="4X6IwqdNkjD" role="3cqZAp" />
        <node concept="3clFbF" id="4X6IwqdNkjE" role="3cqZAp">
          <node concept="37vLTI" id="4X6IwqdNkjF" role="3clFbG">
            <node concept="2OqwBi" id="4X6IwqdNkjG" role="37vLTJ">
              <node concept="37vLTw" id="4X6IwqdNkjH" role="2Oq$k0">
                <ref role="3cqZAo" node="4X6IwqdNkj$" resolve="node" />
              </node>
              <node concept="3TrcHB" id="4X6IwqdNkjI" role="2OqNvi">
                <ref role="3TsBF5" to="tpck:h0TrG11" resolve="name" />
              </node>
            </node>
            <node concept="37vLTw" id="4X6IwqdNkjJ" role="37vLTx">
              <ref role="3cqZAo" node="4X6IwqdNkk5" resolve="name" />
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="4X6IwqdNkjK" role="3cqZAp">
          <node concept="37vLTI" id="4X6IwqdNkjL" role="3clFbG">
            <node concept="37vLTw" id="4X6IwqdNkjM" role="37vLTx">
              <ref role="3cqZAo" node="4X6IwqdNkk7" resolve="virtualPackage" />
            </node>
            <node concept="2OqwBi" id="4X6IwqdNkjN" role="37vLTJ">
              <node concept="37vLTw" id="4X6IwqdNkjO" role="2Oq$k0">
                <ref role="3cqZAo" node="4X6IwqdNkj$" resolve="node" />
              </node>
              <node concept="3TrcHB" id="4X6IwqdNkjP" role="2OqNvi">
                <ref role="3TsBF5" to="tpck:hnGE5uv" resolve="virtualPackage" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="4X6IwqdNkjQ" role="3cqZAp">
          <node concept="37vLTI" id="4X6IwqdNkjR" role="3clFbG">
            <node concept="2YIFZM" id="4X6IwqdNkjS" role="37vLTx">
              <ref role="1Pybhc" to="twe9:1o9qxtfbpaa" resolve="ConceptIdUtil" />
              <ref role="37wK5l" to="twe9:1o9qxtfbpgW" resolve="generate" />
              <node concept="37vLTw" id="4X6IwqdNkjT" role="37wK5m">
                <ref role="3cqZAo" node="4X6IwqdNkj$" resolve="node" />
              </node>
              <node concept="2OqwBi" id="4X6IwqdNkjU" role="37wK5m">
                <node concept="Xjq3P" id="4X6IwqdNkjV" role="2Oq$k0" />
                <node concept="2OwXpG" id="4X6IwqdNkjW" role="2OqNvi">
                  <ref role="2Oxat5" node="4X6IwqdNjpw" resolve="structureModel" />
                </node>
              </node>
            </node>
            <node concept="2OqwBi" id="4X6IwqdNkjX" role="37vLTJ">
              <node concept="37vLTw" id="4X6IwqdNkjY" role="2Oq$k0">
                <ref role="3cqZAo" node="4X6IwqdNkj$" resolve="node" />
              </node>
              <node concept="3TrcHB" id="4X6IwqdNkjZ" role="2OqNvi">
                <ref role="3TsBF5" to="tpce:1o9qxtf9pgp" resolve="conceptId" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="4X6IwqdNkk0" role="3cqZAp" />
        <node concept="3cpWs6" id="4X6IwqdNkk1" role="3cqZAp">
          <node concept="37vLTw" id="4X6IwqdNkk2" role="3cqZAk">
            <ref role="3cqZAo" node="4X6IwqdNkj$" resolve="node" />
          </node>
        </node>
      </node>
      <node concept="3uibUv" id="4X6IwqdNkk4" role="3clF45">
        <ref role="3uigEE" to="mhbf:~SNode" resolve="SNode" />
      </node>
      <node concept="37vLTG" id="4X6IwqdNkk5" role="3clF46">
        <property role="TrG5h" value="name" />
        <node concept="17QB3L" id="4X6IwqdNkk6" role="1tU5fm" />
      </node>
      <node concept="37vLTG" id="4X6IwqdNkk7" role="3clF46">
        <property role="TrG5h" value="virtualPackage" />
        <node concept="17QB3L" id="4X6IwqdNkk8" role="1tU5fm" />
      </node>
      <node concept="3Tm1VV" id="4X6IwqdNkk3" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="5Jh2F9ezbnX" role="jymVt" />
    <node concept="3clFb_" id="4X6IwqdNks2" role="jymVt">
      <property role="TrG5h" value="createConstraintDataType" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="4X6IwqdNks4" role="3clF47">
        <node concept="3cpWs8" id="4X6IwqdNks5" role="3cqZAp">
          <node concept="3cpWsn" id="4X6IwqdNks6" role="3cpWs9">
            <property role="TrG5h" value="node" />
            <node concept="3Tqbb2" id="4X6IwqdNks7" role="1tU5fm">
              <ref role="ehGHo" to="tpce:fKAz7CR" resolve="ConstrainedDataTypeDeclaration" />
            </node>
            <node concept="2ShNRf" id="4X6IwqdNks8" role="33vP2m">
              <node concept="3zrR0B" id="4X6IwqdNks9" role="2ShVmc">
                <node concept="3Tqbb2" id="4X6IwqdNksa" role="3zrR0E">
                  <ref role="ehGHo" to="tpce:fKAz7CR" resolve="ConstrainedDataTypeDeclaration" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="4X6IwqdNksb" role="3cqZAp" />
        <node concept="3clFbF" id="4X6IwqdNksc" role="3cqZAp">
          <node concept="37vLTI" id="4X6IwqdNksd" role="3clFbG">
            <node concept="2OqwBi" id="4X6IwqdNkse" role="37vLTJ">
              <node concept="37vLTw" id="4X6IwqdNksf" role="2Oq$k0">
                <ref role="3cqZAo" node="4X6IwqdNks6" resolve="node" />
              </node>
              <node concept="3TrcHB" id="4X6IwqdNksg" role="2OqNvi">
                <ref role="3TsBF5" to="tpck:h0TrG11" resolve="name" />
              </node>
            </node>
            <node concept="37vLTw" id="4X6IwqdNksh" role="37vLTx">
              <ref role="3cqZAo" node="4X6IwqdNksz" resolve="name" />
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="4X6IwqdNksi" role="3cqZAp">
          <node concept="37vLTI" id="4X6IwqdNksj" role="3clFbG">
            <node concept="37vLTw" id="4X6IwqdNksk" role="37vLTx">
              <ref role="3cqZAo" node="4X6IwqdNksB" resolve="virtualPackage" />
            </node>
            <node concept="2OqwBi" id="4X6IwqdNksl" role="37vLTJ">
              <node concept="37vLTw" id="4X6IwqdNksm" role="2Oq$k0">
                <ref role="3cqZAo" node="4X6IwqdNks6" resolve="node" />
              </node>
              <node concept="3TrcHB" id="4X6IwqdNksn" role="2OqNvi">
                <ref role="3TsBF5" to="tpck:hnGE5uv" resolve="virtualPackage" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="4X6IwqdNkso" role="3cqZAp">
          <node concept="37vLTI" id="4X6IwqdNksp" role="3clFbG">
            <node concept="37vLTw" id="4X6IwqdNksq" role="37vLTx">
              <ref role="3cqZAo" node="4X6IwqdNks_" resolve="constraint" />
            </node>
            <node concept="2OqwBi" id="4X6IwqdNksr" role="37vLTJ">
              <node concept="37vLTw" id="4X6IwqdNkss" role="2Oq$k0">
                <ref role="3cqZAo" node="4X6IwqdNks6" resolve="node" />
              </node>
              <node concept="3TrcHB" id="4X6IwqdNkst" role="2OqNvi">
                <ref role="3TsBF5" to="tpce:fKFLfW2" resolve="constraint" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="4X6IwqdNksu" role="3cqZAp" />
        <node concept="3cpWs6" id="4X6IwqdNksv" role="3cqZAp">
          <node concept="37vLTw" id="4X6IwqdNksw" role="3cqZAk">
            <ref role="3cqZAo" node="4X6IwqdNks6" resolve="node" />
          </node>
        </node>
      </node>
      <node concept="3uibUv" id="4X6IwqdNksy" role="3clF45">
        <ref role="3uigEE" to="mhbf:~SNode" resolve="SNode" />
      </node>
      <node concept="37vLTG" id="4X6IwqdNksz" role="3clF46">
        <property role="TrG5h" value="name" />
        <node concept="17QB3L" id="4X6IwqdNks$" role="1tU5fm" />
      </node>
      <node concept="37vLTG" id="4X6IwqdNks_" role="3clF46">
        <property role="TrG5h" value="constraint" />
        <node concept="17QB3L" id="4X6IwqdNksA" role="1tU5fm" />
      </node>
      <node concept="37vLTG" id="4X6IwqdNksB" role="3clF46">
        <property role="TrG5h" value="virtualPackage" />
        <node concept="17QB3L" id="4X6IwqdNksC" role="1tU5fm" />
      </node>
      <node concept="3Tm1VV" id="4X6IwqdNksx" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="Pei6ieTR2v" role="jymVt" />
    <node concept="3Tm1VV" id="Pei6ieTQT$" role="1B3o_S" />
    <node concept="3UR2Jj" id="Pei6ieTR1X" role="lGtFl">
      <node concept="TZ5HA" id="Pei6ieTR1Y" role="TZ5H$">
        <node concept="1dT_AC" id="Pei6ieTR1Z" role="1dT_Ay">
          <property role="1dT_AB" value="Helper class for creating new SNodes." />
        </node>
      </node>
    </node>
  </node>
  <node concept="312cEu" id="Pei6ieTS8A">
    <property role="TrG5h" value="NodeHelper" />
    <node concept="2YIFZL" id="5Jh2F9ezcou" role="jymVt">
      <property role="TrG5h" value="linkInterfaceToConcept" />
      <property role="od$2w" value="false" />
      <property role="DiZV1" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="5Jh2F9ezcox" role="3clF47">
        <node concept="3cpWs8" id="5Jh2F9ezcyg" role="3cqZAp">
          <node concept="3cpWsn" id="5Jh2F9ezcyj" role="3cpWs9">
            <property role="TrG5h" value="conceptDeclaration" />
            <node concept="3Tqbb2" id="5Jh2F9ezcyf" role="1tU5fm">
              <ref role="ehGHo" to="tpce:f_TIwhg" resolve="ConceptDeclaration" />
            </node>
            <node concept="10QFUN" id="5Jh2F9ezusd" role="33vP2m">
              <node concept="3Tqbb2" id="5Jh2F9ezusM" role="10QFUM">
                <ref role="ehGHo" to="tpce:f_TIwhg" resolve="ConceptDeclaration" />
              </node>
              <node concept="37vLTw" id="5Jh2F9ezurz" role="10QFUP">
                <ref role="3cqZAo" node="5Jh2F9ezcqI" resolve="concept" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="5Jh2F9ezutW" role="3cqZAp">
          <node concept="3cpWsn" id="5Jh2F9ezutX" role="3cpWs9">
            <property role="TrG5h" value="interfaceDeclaration" />
            <node concept="3Tqbb2" id="5Jh2F9ezutY" role="1tU5fm">
              <ref role="ehGHo" to="tpce:h0PlHMJ" resolve="InterfaceConceptDeclaration" />
            </node>
            <node concept="10QFUN" id="5Jh2F9ezutZ" role="33vP2m">
              <node concept="3Tqbb2" id="5Jh2F9ezuu0" role="10QFUM">
                <ref role="ehGHo" to="tpce:h0PlHMJ" resolve="InterfaceConceptDeclaration" />
              </node>
              <node concept="37vLTw" id="5Jh2F9ezuvw" role="10QFUP">
                <ref role="3cqZAo" node="5Jh2F9ezcrR" resolve="iface" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5Jh2F9ezwmu" role="3cqZAp" />
        <node concept="3cpWs8" id="5Jh2F9ezwpO" role="3cqZAp">
          <node concept="3cpWsn" id="5Jh2F9ezwpU" role="3cpWs9">
            <property role="TrG5h" value="reference" />
            <node concept="3Tqbb2" id="5Jh2F9ezwqQ" role="1tU5fm">
              <ref role="ehGHo" to="tpce:h0PrWoo" resolve="InterfaceConceptReference" />
            </node>
            <node concept="2ShNRf" id="5Jh2F9ezwru" role="33vP2m">
              <node concept="3zrR0B" id="5Jh2F9ezwAr" role="2ShVmc">
                <node concept="3Tqbb2" id="5Jh2F9ezwAt" role="3zrR0E">
                  <ref role="ehGHo" to="tpce:h0PrWoo" resolve="InterfaceConceptReference" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="5Jh2F9ezwDo" role="3cqZAp">
          <node concept="37vLTI" id="5Jh2F9ezwMy" role="3clFbG">
            <node concept="37vLTw" id="5Jh2F9ezwOj" role="37vLTx">
              <ref role="3cqZAo" node="5Jh2F9ezutX" resolve="interfaceDeclaration" />
            </node>
            <node concept="2OqwBi" id="5Jh2F9ezwFH" role="37vLTJ">
              <node concept="37vLTw" id="5Jh2F9ezwDm" role="2Oq$k0">
                <ref role="3cqZAo" node="5Jh2F9ezwpU" resolve="reference" />
              </node>
              <node concept="3TrEf2" id="5Jh2F9ezwJe" role="2OqNvi">
                <ref role="3Tt5mk" to="tpce:h0PrY0D" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5Jh2F9ezwBT" role="3cqZAp" />
        <node concept="3clFbF" id="5Jh2F9ezuyK" role="3cqZAp">
          <node concept="2OqwBi" id="5Jh2F9ezvdY" role="3clFbG">
            <node concept="2OqwBi" id="5Jh2F9ezu_V" role="2Oq$k0">
              <node concept="37vLTw" id="5Jh2F9ezuyI" role="2Oq$k0">
                <ref role="3cqZAo" node="5Jh2F9ezcyj" resolve="conceptDeclaration" />
              </node>
              <node concept="3Tsc0h" id="5Jh2F9ezuOa" role="2OqNvi">
                <ref role="3TtcxE" to="tpce:h0Pzm$Y" />
              </node>
            </node>
            <node concept="TSZUe" id="5Jh2F9ezw1C" role="2OqNvi">
              <node concept="37vLTw" id="5Jh2F9ezwQ5" role="25WWJ7">
                <ref role="3cqZAo" node="5Jh2F9ezwpU" resolve="reference" />
              </node>
            </node>
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="5Jh2F9ezcmg" role="1B3o_S" />
      <node concept="3cqZAl" id="5Jh2F9ezcos" role="3clF45" />
      <node concept="37vLTG" id="5Jh2F9ezcqI" role="3clF46">
        <property role="TrG5h" value="concept" />
        <node concept="3uibUv" id="5Jh2F9ezcrA" role="1tU5fm">
          <ref role="3uigEE" to="mhbf:~SNode" resolve="SNode" />
        </node>
      </node>
      <node concept="37vLTG" id="5Jh2F9ezcrR" role="3clF46">
        <property role="TrG5h" value="iface" />
        <node concept="3uibUv" id="5Jh2F9ezcs5" role="1tU5fm">
          <ref role="3uigEE" to="mhbf:~SNode" resolve="SNode" />
        </node>
      </node>
      <node concept="P$JXv" id="5Jh2F9ezyzL" role="lGtFl">
        <node concept="TZ5HA" id="5Jh2F9ezyzM" role="TZ5H$">
          <node concept="1dT_AC" id="5Jh2F9ezyzN" role="1dT_Ay">
            <property role="1dT_AB" value="Links an interface node and a concept node together (adds the interface to &quot;implements&quot; field)." />
          </node>
        </node>
      </node>
    </node>
    <node concept="2tJIrI" id="5Jh2F9e$iv5" role="jymVt" />
    <node concept="2YIFZL" id="5Jh2F9e$iKt" role="jymVt">
      <property role="TrG5h" value="addChildToNode" />
      <property role="od$2w" value="false" />
      <property role="DiZV1" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="5Jh2F9e$iKw" role="3clF47">
        <node concept="3cpWs8" id="5Jh2F9e$_uU" role="3cqZAp">
          <node concept="3cpWsn" id="5Jh2F9e$_uV" role="3cpWs9">
            <property role="TrG5h" value="parentDecl" />
            <node concept="3Tqbb2" id="5Jh2F9e$_uW" role="1tU5fm">
              <ref role="ehGHo" to="tpce:f_TIwhg" resolve="ConceptDeclaration" />
            </node>
            <node concept="10QFUN" id="5Jh2F9e$_uX" role="33vP2m">
              <node concept="3Tqbb2" id="5Jh2F9e$_uY" role="10QFUM">
                <ref role="ehGHo" to="tpce:f_TIwhg" resolve="ConceptDeclaration" />
              </node>
              <node concept="37vLTw" id="5Jh2F9e$_uZ" role="10QFUP">
                <ref role="3cqZAo" node="5Jh2F9e$iQu" resolve="parent" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="5Jh2F9e$iSs" role="3cqZAp">
          <node concept="3cpWsn" id="5Jh2F9e$iSv" role="3cpWs9">
            <property role="TrG5h" value="childDecl" />
            <node concept="3Tqbb2" id="5Jh2F9e$iSr" role="1tU5fm">
              <ref role="ehGHo" to="tpce:h0PkWnZ" resolve="AbstractConceptDeclaration" />
            </node>
            <node concept="10QFUN" id="5Jh2F9e$iWX" role="33vP2m">
              <node concept="3Tqbb2" id="5Jh2F9e$iXw" role="10QFUM">
                <ref role="ehGHo" to="tpce:h0PkWnZ" resolve="AbstractConceptDeclaration" />
              </node>
              <node concept="37vLTw" id="5Jh2F9e$_Ad" role="10QFUP">
                <ref role="3cqZAo" node="5Jh2F9e$iQE" resolve="child" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5Jh2F9e_tWy" role="3cqZAp" />
        <node concept="3cpWs8" id="5Jh2F9e$jog" role="3cqZAp">
          <node concept="3cpWsn" id="5Jh2F9e$jom" role="3cpWs9">
            <property role="TrG5h" value="link" />
            <node concept="3Tqbb2" id="5Jh2F9e$jp0" role="1tU5fm">
              <ref role="ehGHo" to="tpce:f_TJgxE" resolve="LinkDeclaration" />
            </node>
            <node concept="2ShNRf" id="5Jh2F9e$jq0" role="33vP2m">
              <node concept="3zrR0B" id="5Jh2F9e$jvB" role="2ShVmc">
                <node concept="3Tqbb2" id="5Jh2F9e$jvD" role="3zrR0E">
                  <ref role="ehGHo" to="tpce:f_TJgxE" resolve="LinkDeclaration" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5Jh2F9e_q0n" role="3cqZAp" />
        <node concept="3clFbF" id="5Jh2F9e$jxj" role="3cqZAp">
          <node concept="37vLTI" id="5Jh2F9e$jFQ" role="3clFbG">
            <node concept="2OqwBi" id="5Jh2F9e$jzo" role="37vLTJ">
              <node concept="37vLTw" id="5Jh2F9e$jxh" role="2Oq$k0">
                <ref role="3cqZAo" node="5Jh2F9e$jom" resolve="link" />
              </node>
              <node concept="3TrcHB" id="5Jh2F9e$jEh" role="2OqNvi">
                <ref role="3TsBF5" to="tpce:fA0kJcN" resolve="role" />
              </node>
            </node>
            <node concept="37vLTw" id="5Jh2F9eAcyY" role="37vLTx">
              <ref role="3cqZAo" node="5Jh2F9eAcuK" resolve="name" />
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="5Jh2F9e$jPI" role="3cqZAp">
          <node concept="37vLTI" id="5Jh2F9e$k1m" role="3clFbG">
            <node concept="2OqwBi" id="5Jh2F9e$jSC" role="37vLTJ">
              <node concept="37vLTw" id="5Jh2F9e$jPG" role="2Oq$k0">
                <ref role="3cqZAo" node="5Jh2F9e$jom" resolve="link" />
              </node>
              <node concept="3TrcHB" id="5Jh2F9e$jZL" role="2OqNvi">
                <ref role="3TsBF5" to="tpce:fA0lbG4" resolve="sourceCardinality" />
              </node>
            </node>
            <node concept="2OqwBi" id="Pei6ieVvhf" role="37vLTx">
              <node concept="37vLTw" id="Pei6ieVvdi" role="2Oq$k0">
                <ref role="3cqZAo" node="5Jh2F9e$klv" resolve="quantity" />
              </node>
              <node concept="liA8E" id="Pei6ieVvz8" role="2OqNvi">
                <ref role="37wK5l" to="kr2q:~Quantity.getCardinality():java.lang.String" resolve="getCardinality" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="5Jh2F9e$l0v" role="3cqZAp">
          <node concept="37vLTI" id="5Jh2F9e$ljn" role="3clFbG">
            <node concept="37vLTw" id="5Jh2F9e$_Ep" role="37vLTx">
              <ref role="3cqZAo" node="5Jh2F9e$iSv" resolve="childDecl" />
            </node>
            <node concept="2OqwBi" id="5Jh2F9e$l3O" role="37vLTJ">
              <node concept="37vLTw" id="5Jh2F9e$l0t" role="2Oq$k0">
                <ref role="3cqZAo" node="5Jh2F9e$jom" resolve="link" />
              </node>
              <node concept="3TrEf2" id="5Jh2F9e$lgs" role="2OqNvi">
                <ref role="3Tt5mk" to="tpce:fA0lvVK" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1fTj9HrDHoi" role="3cqZAp" />
        <node concept="3SKdUt" id="5Jh2F9eAcrD" role="3cqZAp">
          <node concept="3SKdUq" id="5Jh2F9eAcrF" role="3SKWNk">
            <property role="3SKdUp" value="This line somehow makes the link add child, not reference" />
          </node>
        </node>
        <node concept="3clFbF" id="5Jh2F9e_7oG" role="3cqZAp">
          <node concept="37vLTI" id="5Jh2F9e_8Fm" role="3clFbG">
            <node concept="3f7Wdw" id="5Jh2F9e_8Hw" role="37vLTx">
              <ref role="3f7vo2" to="tpce:fLJjDmR" resolve="LinkMetaclass" />
              <ref role="3f7u_j" to="tpce:fLJjDmT" />
            </node>
            <node concept="2OqwBi" id="5Jh2F9e_7rZ" role="37vLTJ">
              <node concept="37vLTw" id="5Jh2F9e_7oE" role="2Oq$k0">
                <ref role="3cqZAo" node="5Jh2F9e$jom" resolve="link" />
              </node>
              <node concept="3TrcHB" id="5Jh2F9e_8BT" role="2OqNvi">
                <ref role="3TsBF5" to="tpce:fA0lm$B" resolve="metaClass" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5Jh2F9eA4bT" role="3cqZAp" />
        <node concept="3clFbF" id="5Jh2F9eA4ng" role="3cqZAp">
          <node concept="2OqwBi" id="5Jh2F9eA57T" role="3clFbG">
            <node concept="2OqwBi" id="5Jh2F9eA4td" role="2Oq$k0">
              <node concept="37vLTw" id="5Jh2F9eA4ne" role="2Oq$k0">
                <ref role="3cqZAo" node="5Jh2F9e$_uV" resolve="parentDecl" />
              </node>
              <node concept="3Tsc0h" id="5Jh2F9eA4$w" role="2OqNvi">
                <ref role="3TtcxE" to="tpce:f_TKVDF" />
              </node>
            </node>
            <node concept="TSZUe" id="5Jh2F9eA7rV" role="2OqNvi">
              <node concept="37vLTw" id="5Jh2F9eA7A2" role="25WWJ7">
                <ref role="3cqZAo" node="5Jh2F9e$jom" resolve="link" />
              </node>
            </node>
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="5Jh2F9e$iEz" role="1B3o_S" />
      <node concept="3cqZAl" id="5Jh2F9e$iKq" role="3clF45" />
      <node concept="37vLTG" id="5Jh2F9e$iQu" role="3clF46">
        <property role="TrG5h" value="parent" />
        <node concept="3uibUv" id="5Jh2F9e$iQt" role="1tU5fm">
          <ref role="3uigEE" to="mhbf:~SNode" resolve="SNode" />
        </node>
      </node>
      <node concept="37vLTG" id="5Jh2F9e$iQE" role="3clF46">
        <property role="TrG5h" value="child" />
        <node concept="3uibUv" id="5Jh2F9e$iQW" role="1tU5fm">
          <ref role="3uigEE" to="mhbf:~SNode" resolve="SNode" />
        </node>
      </node>
      <node concept="37vLTG" id="5Jh2F9eAcuK" role="3clF46">
        <property role="TrG5h" value="name" />
        <node concept="17QB3L" id="5Jh2F9eAcwm" role="1tU5fm" />
      </node>
      <node concept="37vLTG" id="5Jh2F9e$klv" role="3clF46">
        <property role="TrG5h" value="quantity" />
        <node concept="3uibUv" id="Pei6ieVjJQ" role="1tU5fm">
          <ref role="3uigEE" to="kr2q:~Quantity" resolve="Quantity" />
        </node>
      </node>
      <node concept="P$JXv" id="5Jh2F9eAcmt" role="lGtFl">
        <node concept="TZ5HA" id="5Jh2F9eAcmu" role="TZ5H$">
          <node concept="1dT_AC" id="5Jh2F9eAcmv" role="1dT_Ay">
            <property role="1dT_AB" value="Adds a child concept to a parent concept with given cardinality." />
          </node>
        </node>
      </node>
    </node>
    <node concept="2tJIrI" id="5Jh2F9eAeiW" role="jymVt" />
    <node concept="2YIFZL" id="5Jh2F9eAeF4" role="jymVt">
      <property role="TrG5h" value="addPropertyToNode" />
      <property role="od$2w" value="false" />
      <property role="DiZV1" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="5Jh2F9eAeF7" role="3clF47">
        <node concept="3cpWs8" id="5Jh2F9eAePp" role="3cqZAp">
          <node concept="3cpWsn" id="5Jh2F9eAePq" role="3cpWs9">
            <property role="TrG5h" value="nodeDecl" />
            <node concept="3Tqbb2" id="5Jh2F9eAePr" role="1tU5fm">
              <ref role="ehGHo" to="tpce:f_TIwhg" resolve="ConceptDeclaration" />
            </node>
            <node concept="10QFUN" id="5Jh2F9eAePs" role="33vP2m">
              <node concept="3Tqbb2" id="5Jh2F9eAePt" role="10QFUM">
                <ref role="ehGHo" to="tpce:f_TIwhg" resolve="ConceptDeclaration" />
              </node>
              <node concept="37vLTw" id="5Jh2F9eAeRQ" role="10QFUP">
                <ref role="3cqZAo" node="5Jh2F9eAeNs" resolve="node" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="52zxGLo51Ry" role="3cqZAp">
          <node concept="3cpWsn" id="52zxGLo51R_" role="3cpWs9">
            <property role="TrG5h" value="propertyDecl" />
            <node concept="3Tqbb2" id="52zxGLo51Rx" role="1tU5fm">
              <ref role="ehGHo" to="tpce:f_TJgxF" resolve="PropertyDeclaration" />
            </node>
            <node concept="2ShNRf" id="52zxGLo54ik" role="33vP2m">
              <node concept="3zrR0B" id="52zxGLo54ii" role="2ShVmc">
                <node concept="3Tqbb2" id="52zxGLo54ij" role="3zrR0E">
                  <ref role="ehGHo" to="tpce:f_TJgxF" resolve="PropertyDeclaration" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="5Jh2F9eAiHp" role="3cqZAp">
          <node concept="3cpWsn" id="5Jh2F9eAiHv" role="3cpWs9">
            <property role="TrG5h" value="typeDecl" />
            <node concept="3Tqbb2" id="5Jh2F9eAiIp" role="1tU5fm">
              <ref role="ehGHo" to="tpce:fKAxPRU" resolve="DataTypeDeclaration" />
            </node>
            <node concept="10QFUN" id="5Jh2F9eAiL7" role="33vP2m">
              <node concept="3Tqbb2" id="5Jh2F9eAiME" role="10QFUM">
                <ref role="ehGHo" to="tpce:fKAxPRU" resolve="DataTypeDeclaration" />
              </node>
              <node concept="37vLTw" id="5Jh2F9eAiOy" role="10QFUP">
                <ref role="3cqZAo" node="5Jh2F9eAeO0" resolve="type" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5Jh2F9eAiD_" role="3cqZAp" />
        <node concept="3clFbF" id="5Jh2F9eAiV8" role="3cqZAp">
          <node concept="37vLTI" id="5Jh2F9eAj8C" role="3clFbG">
            <node concept="37vLTw" id="5Jh2F9eAjaY" role="37vLTx">
              <ref role="3cqZAo" node="5Jh2F9eAeNA" resolve="name" />
            </node>
            <node concept="2OqwBi" id="5Jh2F9eAiXR" role="37vLTJ">
              <node concept="37vLTw" id="5Jh2F9eAjqb" role="2Oq$k0">
                <ref role="3cqZAo" node="52zxGLo51R_" resolve="propertyDecl" />
              </node>
              <node concept="3TrcHB" id="5Jh2F9eAj46" role="2OqNvi">
                <ref role="3TsBF5" to="tpck:h0TrG11" resolve="name" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="5Jh2F9eAjdQ" role="3cqZAp">
          <node concept="37vLTI" id="5Jh2F9eAjzt" role="3clFbG">
            <node concept="37vLTw" id="5Jh2F9eAj_K" role="37vLTx">
              <ref role="3cqZAo" node="5Jh2F9eAiHv" resolve="typeDecl" />
            </node>
            <node concept="2OqwBi" id="5Jh2F9eAjgL" role="37vLTJ">
              <node concept="37vLTw" id="5Jh2F9eAjse" role="2Oq$k0">
                <ref role="3cqZAo" node="52zxGLo51R_" resolve="propertyDecl" />
              </node>
              <node concept="3TrEf2" id="5Jh2F9eAjye" role="2OqNvi">
                <ref role="3Tt5mk" to="tpce:fKAX2Z_" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5Jh2F9eAiEg" role="3cqZAp" />
        <node concept="3clFbF" id="5Jh2F9eAfbD" role="3cqZAp">
          <node concept="2OqwBi" id="5Jh2F9eAg0W" role="3clFbG">
            <node concept="2OqwBi" id="5Jh2F9eAffk" role="2Oq$k0">
              <node concept="37vLTw" id="5Jh2F9eAfbB" role="2Oq$k0">
                <ref role="3cqZAo" node="5Jh2F9eAePq" resolve="nodeDecl" />
              </node>
              <node concept="3Tsc0h" id="5Jh2F9eAftz" role="2OqNvi">
                <ref role="3TtcxE" to="tpce:f_TKVDG" />
              </node>
            </node>
            <node concept="TSZUe" id="5Jh2F9eAikY" role="2OqNvi">
              <node concept="37vLTw" id="5Jh2F9eAiw4" role="25WWJ7">
                <ref role="3cqZAo" node="52zxGLo51R_" resolve="propertyDecl" />
              </node>
            </node>
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="5Jh2F9eAeyU" role="1B3o_S" />
      <node concept="3cqZAl" id="5Jh2F9eAeF1" role="3clF45" />
      <node concept="37vLTG" id="5Jh2F9eAeNs" role="3clF46">
        <property role="TrG5h" value="node" />
        <node concept="3uibUv" id="5Jh2F9eAeNr" role="1tU5fm">
          <ref role="3uigEE" to="mhbf:~SNode" resolve="SNode" />
        </node>
      </node>
      <node concept="37vLTG" id="5Jh2F9eAeNA" role="3clF46">
        <property role="TrG5h" value="name" />
        <node concept="17QB3L" id="5Jh2F9eAeNS" role="1tU5fm" />
      </node>
      <node concept="37vLTG" id="5Jh2F9eAeO0" role="3clF46">
        <property role="TrG5h" value="type" />
        <node concept="3uibUv" id="5Jh2F9eAlA6" role="1tU5fm">
          <ref role="3uigEE" to="mhbf:~SNode" resolve="SNode" />
        </node>
      </node>
      <node concept="P$JXv" id="5Jh2F9eAqw4" role="lGtFl">
        <node concept="TZ5HA" id="5Jh2F9eAqw5" role="TZ5H$">
          <node concept="1dT_AC" id="5Jh2F9eAqw6" role="1dT_Ay">
            <property role="1dT_AB" value="Adds a target regex rule's data constraint type as a property to node." />
          </node>
        </node>
      </node>
    </node>
    <node concept="2tJIrI" id="2Dfqp1I661n" role="jymVt" />
    <node concept="2YIFZL" id="2Dfqp1I661o" role="jymVt">
      <property role="TrG5h" value="getProperty" />
      <property role="od$2w" value="false" />
      <property role="DiZV1" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="2Dfqp1I661p" role="3clF47">
        <node concept="1DcWWT" id="2Dfqp1I661q" role="3cqZAp">
          <node concept="3clFbS" id="2Dfqp1I661r" role="2LFqv$">
            <node concept="3clFbJ" id="2Dfqp1I661s" role="3cqZAp">
              <node concept="3clFbS" id="2Dfqp1I661t" role="3clFbx">
                <node concept="3cpWs6" id="2Dfqp1I661u" role="3cqZAp">
                  <node concept="37vLTw" id="2Dfqp1I661v" role="3cqZAk">
                    <ref role="3cqZAo" node="2Dfqp1I661A" resolve="prop" />
                  </node>
                </node>
              </node>
              <node concept="2OqwBi" id="2Dfqp1I661w" role="3clFbw">
                <node concept="37vLTw" id="2Dfqp1I661x" role="2Oq$k0">
                  <ref role="3cqZAo" node="2Dfqp1I661M" resolve="propertyName" />
                </node>
                <node concept="liA8E" id="2Dfqp1I661y" role="2OqNvi">
                  <ref role="37wK5l" to="wyt6:~String.equals(java.lang.Object):boolean" resolve="equals" />
                  <node concept="2OqwBi" id="2Dfqp1I661z" role="37wK5m">
                    <node concept="37vLTw" id="2Dfqp1I661$" role="2Oq$k0">
                      <ref role="3cqZAo" node="2Dfqp1I661A" resolve="prop" />
                    </node>
                    <node concept="3TrcHB" id="2Dfqp1I661_" role="2OqNvi">
                      <ref role="3TsBF5" to="tpck:h0TrG11" resolve="name" />
                    </node>
                  </node>
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWsn" id="2Dfqp1I661A" role="1Duv9x">
            <property role="TrG5h" value="prop" />
            <node concept="3Tqbb2" id="2Dfqp1I661B" role="1tU5fm">
              <ref role="ehGHo" to="tpce:f_TJgxF" resolve="PropertyDeclaration" />
            </node>
          </node>
          <node concept="2OqwBi" id="2Dfqp1I661C" role="1DdaDG">
            <node concept="37vLTw" id="2Dfqp1I661D" role="2Oq$k0">
              <ref role="3cqZAo" node="2Dfqp1I661K" resolve="node" />
            </node>
            <node concept="3Tsc0h" id="2Dfqp1I661E" role="2OqNvi">
              <ref role="3TtcxE" to="tpce:f_TKVDG" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="2Dfqp1I661F" role="3cqZAp" />
        <node concept="3cpWs6" id="2Dfqp1I661G" role="3cqZAp">
          <node concept="10Nm6u" id="2Dfqp1I661H" role="3cqZAk" />
        </node>
      </node>
      <node concept="3Tm1VV" id="2Dfqp1I661I" role="1B3o_S" />
      <node concept="3Tqbb2" id="2Dfqp1I661J" role="3clF45">
        <ref role="ehGHo" to="tpce:f_TJgxF" resolve="PropertyDeclaration" />
      </node>
      <node concept="37vLTG" id="2Dfqp1I661K" role="3clF46">
        <property role="TrG5h" value="node" />
        <node concept="3Tqbb2" id="2Dfqp1I661L" role="1tU5fm">
          <ref role="ehGHo" to="tpce:h0PkWnZ" resolve="AbstractConceptDeclaration" />
        </node>
      </node>
      <node concept="37vLTG" id="2Dfqp1I661M" role="3clF46">
        <property role="TrG5h" value="propertyName" />
        <node concept="17QB3L" id="2Dfqp1I661N" role="1tU5fm" />
      </node>
    </node>
    <node concept="2tJIrI" id="1fTj9HrD$98" role="jymVt" />
    <node concept="2YIFZL" id="4X6IwqdOESX" role="jymVt">
      <property role="TrG5h" value="setProperty" />
      <property role="od$2w" value="false" />
      <property role="DiZV1" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="4X6IwqdOESY" role="3clF47">
        <node concept="3KaCP$" id="2Dfqp1I6vIe" role="3cqZAp">
          <node concept="37vLTw" id="2Dfqp1I6vJK" role="3KbGdf">
            <ref role="3cqZAo" node="4X6IwqdOETs" resolve="property" />
          </node>
          <node concept="3clFbS" id="2Dfqp1I6vIg" role="3Kb1Dw">
            <node concept="YS8fn" id="2Dfqp1I6wtb" role="3cqZAp">
              <node concept="2ShNRf" id="2Dfqp1I6Dem" role="YScLw">
                <node concept="1pGfFk" id="2Dfqp1I6DwZ" role="2ShVmc">
                  <ref role="37wK5l" to="wyt6:~IllegalArgumentException.&lt;init&gt;()" resolve="IllegalArgumentException" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3KbdKl" id="2Dfqp1I6vJP" role="3KbHQx">
            <node concept="Rm8GO" id="Pei6ieUl1k" role="3Kbmr1">
              <ref role="Rm8GQ" node="Pei6ieU45y" resolve="Alias" />
              <ref role="1Px2BO" node="Pei6ieU44m" resolve="Property" />
            </node>
            <node concept="3clFbS" id="2Dfqp1I6vJR" role="3Kbo56">
              <node concept="3clFbF" id="2Dfqp1I6vNg" role="3cqZAp">
                <node concept="2OqwBi" id="2Dfqp1I6weG" role="3clFbG">
                  <node concept="2OqwBi" id="2Dfqp1I6vPt" role="2Oq$k0">
                    <node concept="37vLTw" id="2Dfqp1I6vNf" role="2Oq$k0">
                      <ref role="3cqZAo" node="4X6IwqdOETq" resolve="node" />
                    </node>
                    <node concept="3TrcHB" id="2Dfqp1I7cKu" role="2OqNvi">
                      <ref role="3TsBF5" to="tpce:4qF2Hm2r7ja" resolve="conceptAlias" />
                    </node>
                  </node>
                  <node concept="tyxLq" id="2Dfqp1I6wpR" role="2OqNvi">
                    <node concept="37vLTw" id="2Dfqp1I6wqV" role="tz02z">
                      <ref role="3cqZAo" node="2Dfqp1I67B5" resolve="value" />
                    </node>
                  </node>
                </node>
              </node>
              <node concept="3zACq4" id="2Dfqp1I6MGL" role="3cqZAp" />
            </node>
          </node>
          <node concept="3KbdKl" id="2Dfqp1I6wrd" role="3KbHQx">
            <node concept="Rm8GO" id="Pei6ieUl2o" role="3Kbmr1">
              <ref role="Rm8GQ" node="Pei6ieU45K" resolve="Name" />
              <ref role="1Px2BO" node="Pei6ieU44m" resolve="Property" />
            </node>
            <node concept="3clFbS" id="2Dfqp1I6wrf" role="3Kbo56">
              <node concept="3clFbF" id="2Dfqp1I6wxV" role="3cqZAp">
                <node concept="2OqwBi" id="2Dfqp1I6wU5" role="3clFbG">
                  <node concept="2OqwBi" id="2Dfqp1I6w$8" role="2Oq$k0">
                    <node concept="37vLTw" id="2Dfqp1I6wxU" role="2Oq$k0">
                      <ref role="3cqZAo" node="4X6IwqdOETq" resolve="node" />
                    </node>
                    <node concept="3TrcHB" id="2Dfqp1I6wLQ" role="2OqNvi">
                      <ref role="3TsBF5" to="tpck:h0TrG11" resolve="name" />
                    </node>
                  </node>
                  <node concept="tyxLq" id="2Dfqp1I6x5g" role="2OqNvi">
                    <node concept="37vLTw" id="2Dfqp1I6x6k" role="tz02z">
                      <ref role="3cqZAo" node="2Dfqp1I67B5" resolve="value" />
                    </node>
                  </node>
                </node>
              </node>
              <node concept="3zACq4" id="2Dfqp1I6MHM" role="3cqZAp" />
            </node>
          </node>
          <node concept="3KbdKl" id="2Dfqp1I6x6x" role="3KbHQx">
            <node concept="Rm8GO" id="Pei6ieUl3q" role="3Kbmr1">
              <ref role="Rm8GQ" node="Pei6ieU45W" resolve="VirtualPackage" />
              <ref role="1Px2BO" node="Pei6ieU44m" resolve="Property" />
            </node>
            <node concept="3clFbS" id="2Dfqp1I6x6z" role="3Kbo56">
              <node concept="3clFbF" id="2Dfqp1I6xaJ" role="3cqZAp">
                <node concept="2OqwBi" id="2Dfqp1I6xA7" role="3clFbG">
                  <node concept="2OqwBi" id="2Dfqp1I6xcW" role="2Oq$k0">
                    <node concept="37vLTw" id="2Dfqp1I6xaI" role="2Oq$k0">
                      <ref role="3cqZAo" node="4X6IwqdOETq" resolve="node" />
                    </node>
                    <node concept="3TrcHB" id="2Dfqp1I6xqE" role="2OqNvi">
                      <ref role="3TsBF5" to="tpck:hnGE5uv" resolve="virtualPackage" />
                    </node>
                  </node>
                  <node concept="tyxLq" id="2Dfqp1I6xFW" role="2OqNvi">
                    <node concept="37vLTw" id="2Dfqp1I6xH0" role="tz02z">
                      <ref role="3cqZAo" node="2Dfqp1I67B5" resolve="value" />
                    </node>
                  </node>
                </node>
              </node>
              <node concept="3zACq4" id="2Dfqp1I6MTU" role="3cqZAp" />
            </node>
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="4X6IwqdOETo" role="1B3o_S" />
      <node concept="37vLTG" id="4X6IwqdOETq" role="3clF46">
        <property role="TrG5h" value="node" />
        <node concept="3Tqbb2" id="4X6IwqdOETr" role="1tU5fm">
          <ref role="ehGHo" to="tpce:h0PkWnZ" resolve="AbstractConceptDeclaration" />
        </node>
      </node>
      <node concept="37vLTG" id="4X6IwqdOETs" role="3clF46">
        <property role="TrG5h" value="property" />
        <node concept="3uibUv" id="Pei6ieUl0q" role="1tU5fm">
          <ref role="3uigEE" node="Pei6ieU44m" resolve="Property" />
        </node>
      </node>
      <node concept="37vLTG" id="2Dfqp1I67B5" role="3clF46">
        <property role="TrG5h" value="value" />
        <node concept="17QB3L" id="2Dfqp1I67BZ" role="1tU5fm" />
      </node>
      <node concept="3cqZAl" id="2Dfqp1I68fz" role="3clF45" />
      <node concept="P$JXv" id="1fTj9HrD$ma" role="lGtFl">
        <node concept="TZ5HA" id="1fTj9HrD$mb" role="TZ5H$">
          <node concept="1dT_AC" id="1fTj9HrD$mc" role="1dT_Ay">
            <property role="1dT_AB" value="Sets a given basic (alias, name...) property of a given node to a given value." />
          </node>
        </node>
      </node>
    </node>
    <node concept="2tJIrI" id="1fTj9HrDAiD" role="jymVt" />
    <node concept="2YIFZL" id="1fTj9HrDAz_" role="jymVt">
      <property role="TrG5h" value="setConstraint" />
      <property role="od$2w" value="false" />
      <property role="DiZV1" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="1fTj9HrDAzC" role="3clF47">
        <node concept="3clFbF" id="1fTj9HrDAEA" role="3cqZAp">
          <node concept="37vLTI" id="1fTj9HrDAQq" role="3clFbG">
            <node concept="37vLTw" id="1fTj9HrDASS" role="37vLTx">
              <ref role="3cqZAo" node="1fTj9HrDADu" resolve="value" />
            </node>
            <node concept="2OqwBi" id="1fTj9HrDAG1" role="37vLTJ">
              <node concept="37vLTw" id="1fTj9HrDAE_" role="2Oq$k0">
                <ref role="3cqZAo" node="1fTj9HrDADk" resolve="node" />
              </node>
              <node concept="3TrcHB" id="1fTj9HrDAOR" role="2OqNvi">
                <ref role="3TsBF5" to="tpce:fKFLfW2" resolve="constraint" />
              </node>
            </node>
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="1fTj9HrDAtS" role="1B3o_S" />
      <node concept="3cqZAl" id="1fTj9HrDAzz" role="3clF45" />
      <node concept="37vLTG" id="1fTj9HrDADk" role="3clF46">
        <property role="TrG5h" value="node" />
        <node concept="3Tqbb2" id="1fTj9HrDADj" role="1tU5fm">
          <ref role="ehGHo" to="tpce:fKAz7CR" resolve="ConstrainedDataTypeDeclaration" />
        </node>
      </node>
      <node concept="37vLTG" id="1fTj9HrDADu" role="3clF46">
        <property role="TrG5h" value="value" />
        <node concept="17QB3L" id="1fTj9HrDADA" role="1tU5fm" />
      </node>
      <node concept="P$JXv" id="1fTj9HrDAZs" role="lGtFl">
        <node concept="TZ5HA" id="1fTj9HrDAZt" role="TZ5H$">
          <node concept="1dT_AC" id="1fTj9HrDAZu" role="1dT_Ay">
            <property role="1dT_AB" value="Sets the regex value of a given constrain data type node." />
          </node>
        </node>
      </node>
    </node>
    <node concept="2tJIrI" id="3pfFnS02Gtd" role="jymVt" />
    <node concept="2YIFZL" id="3pfFnS02Gte" role="jymVt">
      <property role="TrG5h" value="getProperty" />
      <property role="od$2w" value="false" />
      <property role="DiZV1" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="3pfFnS02Gtf" role="3clF47">
        <node concept="3cpWs6" id="3pfFnS02Gtg" role="3cqZAp">
          <node concept="2OqwBi" id="3pfFnS02Gth" role="3cqZAk">
            <node concept="2OqwBi" id="3pfFnS02Gti" role="2Oq$k0">
              <node concept="37vLTw" id="3pfFnS02Gtj" role="2Oq$k0">
                <ref role="3cqZAo" node="3pfFnS02Gtp" resolve="node" />
              </node>
              <node concept="3Tsc0h" id="3pfFnS02GKx" role="2OqNvi">
                <ref role="3TtcxE" to="tpce:f_TKVDG" />
              </node>
            </node>
            <node concept="34jXtK" id="3pfFnS02Gtl" role="2OqNvi">
              <node concept="37vLTw" id="3pfFnS02Gtm" role="25WWJ7">
                <ref role="3cqZAo" node="3pfFnS02Gtr" resolve="index" />
              </node>
            </node>
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="3pfFnS02Gtn" role="1B3o_S" />
      <node concept="3Tqbb2" id="3pfFnS02Gto" role="3clF45">
        <ref role="ehGHo" to="tpce:f_TJgxF" resolve="PropertyDeclaration" />
      </node>
      <node concept="37vLTG" id="3pfFnS02Gtp" role="3clF46">
        <property role="TrG5h" value="node" />
        <node concept="3Tqbb2" id="3pfFnS02Gtq" role="1tU5fm">
          <ref role="ehGHo" to="tpce:h0PkWnZ" resolve="AbstractConceptDeclaration" />
        </node>
      </node>
      <node concept="37vLTG" id="3pfFnS02Gtr" role="3clF46">
        <property role="TrG5h" value="index" />
        <node concept="10Oyi0" id="3pfFnS02Gts" role="1tU5fm" />
      </node>
      <node concept="P$JXv" id="1fTj9HrD$x9" role="lGtFl">
        <node concept="TZ5HA" id="1fTj9HrD$xa" role="TZ5H$">
          <node concept="1dT_AC" id="1fTj9HrD$xb" role="1dT_Ay">
            <property role="1dT_AB" value="Returns a property node at specified index of given node." />
          </node>
        </node>
      </node>
    </node>
    <node concept="2tJIrI" id="3pfFnS02$hN" role="jymVt" />
    <node concept="2YIFZL" id="3pfFnS02$hO" role="jymVt">
      <property role="TrG5h" value="getChildLink" />
      <property role="od$2w" value="false" />
      <property role="DiZV1" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="3pfFnS02$hP" role="3clF47">
        <node concept="1DcWWT" id="3pfFnS02$hQ" role="3cqZAp">
          <node concept="3clFbS" id="3pfFnS02$hR" role="2LFqv$">
            <node concept="3clFbJ" id="3pfFnS02$hS" role="3cqZAp">
              <node concept="3clFbS" id="3pfFnS02$hT" role="3clFbx">
                <node concept="3cpWs6" id="3pfFnS02$hU" role="3cqZAp">
                  <node concept="37vLTw" id="3pfFnS02$hV" role="3cqZAk">
                    <ref role="3cqZAo" node="3pfFnS02$i2" resolve="l" />
                  </node>
                </node>
              </node>
              <node concept="2OqwBi" id="3pfFnS02$hW" role="3clFbw">
                <node concept="2OqwBi" id="3pfFnS02$hX" role="2Oq$k0">
                  <node concept="37vLTw" id="3pfFnS02$hY" role="2Oq$k0">
                    <ref role="3cqZAo" node="3pfFnS02$i2" resolve="l" />
                  </node>
                  <node concept="3TrcHB" id="3pfFnS02$hZ" role="2OqNvi">
                    <ref role="3TsBF5" to="tpce:fA0kJcN" resolve="role" />
                  </node>
                </node>
                <node concept="liA8E" id="3pfFnS02$i0" role="2OqNvi">
                  <ref role="37wK5l" to="wyt6:~String.equals(java.lang.Object):boolean" resolve="equals" />
                  <node concept="37vLTw" id="3pfFnS02$i1" role="37wK5m">
                    <ref role="3cqZAo" node="3pfFnS02$ie" resolve="childName" />
                  </node>
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWsn" id="3pfFnS02$i2" role="1Duv9x">
            <property role="TrG5h" value="l" />
            <node concept="3Tqbb2" id="3pfFnS02$i3" role="1tU5fm">
              <ref role="ehGHo" to="tpce:f_TJgxE" resolve="LinkDeclaration" />
            </node>
          </node>
          <node concept="2OqwBi" id="3pfFnS02$i4" role="1DdaDG">
            <node concept="37vLTw" id="3pfFnS02$i5" role="2Oq$k0">
              <ref role="3cqZAo" node="3pfFnS02$ic" resolve="node" />
            </node>
            <node concept="3Tsc0h" id="3pfFnS02$i6" role="2OqNvi">
              <ref role="3TtcxE" to="tpce:f_TKVDF" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="3pfFnS02$i7" role="3cqZAp" />
        <node concept="3cpWs6" id="3pfFnS02$i8" role="3cqZAp">
          <node concept="10Nm6u" id="3pfFnS02$i9" role="3cqZAk" />
        </node>
      </node>
      <node concept="3Tm1VV" id="3pfFnS02$ia" role="1B3o_S" />
      <node concept="3Tqbb2" id="3pfFnS02$ib" role="3clF45">
        <ref role="ehGHo" to="tpce:f_TJgxE" resolve="LinkDeclaration" />
      </node>
      <node concept="37vLTG" id="3pfFnS02$ic" role="3clF46">
        <property role="TrG5h" value="node" />
        <node concept="3Tqbb2" id="3pfFnS02$id" role="1tU5fm">
          <ref role="ehGHo" to="tpce:h0PkWnZ" resolve="AbstractConceptDeclaration" />
        </node>
      </node>
      <node concept="37vLTG" id="3pfFnS02$ie" role="3clF46">
        <property role="TrG5h" value="childName" />
        <node concept="17QB3L" id="3pfFnS02$if" role="1tU5fm" />
      </node>
      <node concept="P$JXv" id="1fTj9HrD$It" role="lGtFl">
        <node concept="TZ5HA" id="1fTj9HrD$Iu" role="TZ5H$">
          <node concept="1dT_AC" id="1fTj9HrD$Iv" role="1dT_Ay">
            <property role="1dT_AB" value="Returns a child link of given node with given name." />
          </node>
        </node>
      </node>
    </node>
    <node concept="2tJIrI" id="4X6IwqdOrOy" role="jymVt" />
    <node concept="2YIFZL" id="4X6IwqdOs4G" role="jymVt">
      <property role="TrG5h" value="getChildLink" />
      <property role="od$2w" value="false" />
      <property role="DiZV1" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="4X6IwqdOs4J" role="3clF47">
        <node concept="3cpWs6" id="4X6IwqdOIZr" role="3cqZAp">
          <node concept="2OqwBi" id="3pfFnS02AMe" role="3cqZAk">
            <node concept="2OqwBi" id="3pfFnS02_ZF" role="2Oq$k0">
              <node concept="37vLTw" id="3pfFnS02_VX" role="2Oq$k0">
                <ref role="3cqZAo" node="4X6IwqdOsad" resolve="node" />
              </node>
              <node concept="3Tsc0h" id="3pfFnS02Af2" role="2OqNvi">
                <ref role="3TtcxE" to="tpce:f_TKVDF" />
              </node>
            </node>
            <node concept="34jXtK" id="3pfFnS02Gjf" role="2OqNvi">
              <node concept="37vLTw" id="3pfFnS02Gmn" role="25WWJ7">
                <ref role="3cqZAo" node="4X6IwqdOsar" resolve="index" />
              </node>
            </node>
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="4X6IwqdOrZe" role="1B3o_S" />
      <node concept="3Tqbb2" id="4X6IwqdOs4D" role="3clF45">
        <ref role="ehGHo" to="tpce:f_TJgxE" resolve="LinkDeclaration" />
      </node>
      <node concept="37vLTG" id="4X6IwqdOsad" role="3clF46">
        <property role="TrG5h" value="node" />
        <node concept="3Tqbb2" id="4X6IwqdOsac" role="1tU5fm">
          <ref role="ehGHo" to="tpce:h0PkWnZ" resolve="AbstractConceptDeclaration" />
        </node>
      </node>
      <node concept="37vLTG" id="4X6IwqdOsar" role="3clF46">
        <property role="TrG5h" value="index" />
        <node concept="10Oyi0" id="3pfFnS02_Kp" role="1tU5fm" />
      </node>
      <node concept="P$JXv" id="1fTj9HrD$Ut" role="lGtFl">
        <node concept="TZ5HA" id="1fTj9HrD$Uu" role="TZ5H$">
          <node concept="1dT_AC" id="1fTj9HrD$Uv" role="1dT_Ay">
            <property role="1dT_AB" value="Returns a child link of given node at given index." />
          </node>
        </node>
      </node>
    </node>
    <node concept="2tJIrI" id="4X6IwqdOFBo" role="jymVt" />
    <node concept="2YIFZL" id="2Dfqp1I5QQQ" role="jymVt">
      <property role="TrG5h" value="getConcepts" />
      <property role="od$2w" value="false" />
      <property role="DiZV1" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="2Dfqp1I5QQT" role="3clF47">
        <node concept="3cpWs6" id="2Dfqp1I5QV$" role="3cqZAp">
          <node concept="2OqwBi" id="2Dfqp1I5UHa" role="3cqZAk">
            <node concept="2OqwBi" id="2Dfqp1I5WPe" role="2Oq$k0">
              <node concept="2OqwBi" id="2Dfqp1I5Sml" role="2Oq$k0">
                <node concept="2OqwBi" id="2Dfqp1I5Rra" role="2Oq$k0">
                  <node concept="37vLTw" id="2Dfqp1I5RpP" role="2Oq$k0">
                    <ref role="3cqZAo" node="2Dfqp1I5RnX" resolve="structureModel" />
                  </node>
                  <node concept="2SmgA7" id="2Dfqp1I5Rs0" role="2OqNvi" />
                </node>
                <node concept="3zZkjj" id="2Dfqp1I5U7S" role="2OqNvi">
                  <node concept="1bVj0M" id="2Dfqp1I5U7U" role="23t8la">
                    <node concept="3clFbS" id="2Dfqp1I5U7V" role="1bW5cS">
                      <node concept="3clFbF" id="2Dfqp1I5UaT" role="3cqZAp">
                        <node concept="2ZW3vV" id="2Dfqp1I5Ufr" role="3clFbG">
                          <node concept="3Tqbb2" id="2Dfqp1I5Ui0" role="2ZW6by">
                            <ref role="ehGHo" to="tpce:f_TIwhg" resolve="ConceptDeclaration" />
                          </node>
                          <node concept="37vLTw" id="2Dfqp1I5UaS" role="2ZW6bz">
                            <ref role="3cqZAo" node="2Dfqp1I5U7W" resolve="it" />
                          </node>
                        </node>
                      </node>
                    </node>
                    <node concept="Rh6nW" id="2Dfqp1I5U7W" role="1bW2Oz">
                      <property role="TrG5h" value="it" />
                      <node concept="2jxLKc" id="2Dfqp1I5U7X" role="1tU5fm" />
                    </node>
                  </node>
                </node>
              </node>
              <node concept="3$u5V9" id="2Dfqp1I5WZS" role="2OqNvi">
                <node concept="1bVj0M" id="2Dfqp1I5WZU" role="23t8la">
                  <node concept="3clFbS" id="2Dfqp1I5WZV" role="1bW5cS">
                    <node concept="3clFbF" id="2Dfqp1I5X6l" role="3cqZAp">
                      <node concept="1PxgMI" id="2Dfqp1I5XcI" role="3clFbG">
                        <ref role="1PxNhF" to="tpce:f_TIwhg" resolve="ConceptDeclaration" />
                        <node concept="37vLTw" id="2Dfqp1I5X6k" role="1PxMeX">
                          <ref role="3cqZAo" node="2Dfqp1I5WZW" resolve="it" />
                        </node>
                      </node>
                    </node>
                  </node>
                  <node concept="Rh6nW" id="2Dfqp1I5WZW" role="1bW2Oz">
                    <property role="TrG5h" value="it" />
                    <node concept="2jxLKc" id="2Dfqp1I5WZX" role="1tU5fm" />
                  </node>
                </node>
              </node>
            </node>
            <node concept="ANE8D" id="2Dfqp1I5WKI" role="2OqNvi" />
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="2Dfqp1I5QMF" role="1B3o_S" />
      <node concept="3uibUv" id="2Dfqp1I5Rki" role="3clF45">
        <ref role="3uigEE" to="33ny:~List" resolve="List" />
        <node concept="3Tqbb2" id="2Dfqp1I5Rku" role="11_B2D">
          <ref role="ehGHo" to="tpce:f_TIwhg" resolve="ConceptDeclaration" />
        </node>
      </node>
      <node concept="37vLTG" id="2Dfqp1I5RnX" role="3clF46">
        <property role="TrG5h" value="structureModel" />
        <node concept="H_c77" id="2Dfqp1I5RnW" role="1tU5fm" />
      </node>
      <node concept="P$JXv" id="1fTj9HrD_0R" role="lGtFl">
        <node concept="TZ5HA" id="1fTj9HrD_0S" role="TZ5H$">
          <node concept="1dT_AC" id="1fTj9HrD_0T" role="1dT_Ay">
            <property role="1dT_AB" value="Returns all classic concepts (not interfaces or data types) from a given model." />
          </node>
        </node>
      </node>
    </node>
    <node concept="3Tm1VV" id="Pei6ieTS8B" role="1B3o_S" />
    <node concept="3UR2Jj" id="Pei6ieTUaW" role="lGtFl">
      <node concept="TZ5HA" id="Pei6ieTUaX" role="TZ5H$">
        <node concept="1dT_AC" id="Pei6ieTUaY" role="1dT_Ay">
          <property role="1dT_AB" value="Helper class encapsulating simple operation with SNodes." />
        </node>
      </node>
    </node>
  </node>
  <node concept="312cEu" id="Pei6ieTWbW">
    <property role="TrG5h" value="TextGenHelper" />
    <node concept="2YIFZL" id="6B2Ha6FnMN5" role="jymVt">
      <property role="TrG5h" value="buildTextGen" />
      <property role="od$2w" value="false" />
      <property role="DiZV1" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="6B2Ha6FnMN8" role="3clF47">
        <node concept="3cpWs8" id="6B2Ha6FnMOh" role="3cqZAp">
          <node concept="3cpWsn" id="6B2Ha6FnMOi" role="3cpWs9">
            <property role="TrG5h" value="textGen" />
            <node concept="3Tqbb2" id="6B2Ha6FnMOj" role="1tU5fm">
              <ref role="ehGHo" to="2omo:hWWtQdD" resolve="ConceptTextGenDeclaration" />
            </node>
            <node concept="2ShNRf" id="6B2Ha6FnMOk" role="33vP2m">
              <node concept="3zrR0B" id="6B2Ha6FnMOl" role="2ShVmc">
                <node concept="3Tqbb2" id="6B2Ha6FnMOm" role="3zrR0E">
                  <ref role="ehGHo" to="2omo:hWWtQdD" resolve="ConceptTextGenDeclaration" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="6B2Ha6FnNcE" role="3cqZAp">
          <node concept="37vLTI" id="6B2Ha6FnNxA" role="3clFbG">
            <node concept="37vLTw" id="6B2Ha6FnN$6" role="37vLTx">
              <ref role="3cqZAo" node="6B2Ha6FnMQx" resolve="node" />
            </node>
            <node concept="2OqwBi" id="6B2Ha6FnNfv" role="37vLTJ">
              <node concept="37vLTw" id="6B2Ha6FnNcC" role="2Oq$k0">
                <ref role="3cqZAo" node="6B2Ha6FnMOi" resolve="textGen" />
              </node>
              <node concept="3TrEf2" id="6B2Ha6FnNuD" role="2OqNvi">
                <ref role="3Tt5mk" to="2omo:hWWuzPd" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="6B2Ha6FoeJK" role="3cqZAp">
          <node concept="37vLTI" id="6B2Ha6Fof7P" role="3clFbG">
            <node concept="2OqwBi" id="6B2Ha6Fofcz" role="37vLTx">
              <node concept="37vLTw" id="6B2Ha6Fof98" role="2Oq$k0">
                <ref role="3cqZAo" node="6B2Ha6FnMQx" resolve="node" />
              </node>
              <node concept="3TrcHB" id="6B2Ha6Fofsm" role="2OqNvi">
                <ref role="3TsBF5" to="tpck:hnGE5uv" resolve="virtualPackage" />
              </node>
            </node>
            <node concept="2OqwBi" id="6B2Ha6FoeQs" role="37vLTJ">
              <node concept="37vLTw" id="6B2Ha6FoeJI" role="2Oq$k0">
                <ref role="3cqZAo" node="6B2Ha6FnMOi" resolve="textGen" />
              </node>
              <node concept="3TrcHB" id="6B2Ha6Fof6g" role="2OqNvi">
                <ref role="3TsBF5" to="tpck:hnGE5uv" resolve="virtualPackage" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="6B2Ha6FnXbK" role="3cqZAp">
          <node concept="37vLTI" id="6B2Ha6FnXbL" role="3clFbG">
            <node concept="2ShNRf" id="6B2Ha6FnXbM" role="37vLTx">
              <node concept="3zrR0B" id="6B2Ha6FnXbN" role="2ShVmc">
                <node concept="3Tqbb2" id="6B2Ha6FnXbO" role="3zrR0E">
                  <ref role="ehGHo" to="2omo:hX1bSug" resolve="GenerateTextDeclaration" />
                </node>
              </node>
            </node>
            <node concept="2OqwBi" id="6B2Ha6FnXbP" role="37vLTJ">
              <node concept="37vLTw" id="6B2Ha6FnXbQ" role="2Oq$k0">
                <ref role="3cqZAo" node="6B2Ha6FnMOi" resolve="textGen" />
              </node>
              <node concept="3TrEf2" id="6B2Ha6FnXbR" role="2OqNvi">
                <ref role="3Tt5mk" to="2omo:hX1c4lS" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="6B2Ha6FnS2Q" role="3cqZAp" />
        <node concept="3cpWs8" id="6B2Ha6FnXgL" role="3cqZAp">
          <node concept="3cpWsn" id="6B2Ha6FnXgR" role="3cpWs9">
            <property role="TrG5h" value="textGenStatements" />
            <node concept="3Tqbb2" id="6B2Ha6FnXix" role="1tU5fm">
              <ref role="ehGHo" to="tpee:fzclF80" resolve="StatementList" />
            </node>
            <node concept="2ShNRf" id="6B2Ha6FnXjA" role="33vP2m">
              <node concept="3zrR0B" id="6B2Ha6FnXA6" role="2ShVmc">
                <node concept="3Tqbb2" id="6B2Ha6FnXA8" role="3zrR0E">
                  <ref role="ehGHo" to="tpee:fzclF80" resolve="StatementList" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="6B2Ha6FnXCq" role="3cqZAp">
          <node concept="37vLTI" id="6B2Ha6FnYFB" role="3clFbG">
            <node concept="37vLTw" id="6B2Ha6FnYHL" role="37vLTx">
              <ref role="3cqZAo" node="6B2Ha6FnXgR" resolve="textGenStatements" />
            </node>
            <node concept="2OqwBi" id="6B2Ha6FnYkL" role="37vLTJ">
              <node concept="2OqwBi" id="6B2Ha6FnXFl" role="2Oq$k0">
                <node concept="37vLTw" id="6B2Ha6FnXCo" role="2Oq$k0">
                  <ref role="3cqZAo" node="6B2Ha6FnMOi" resolve="textGen" />
                </node>
                <node concept="3TrEf2" id="6B2Ha6FnYam" role="2OqNvi">
                  <ref role="3Tt5mk" to="2omo:hX1c4lS" />
                </node>
              </node>
              <node concept="3TrEf2" id="6B2Ha6FnYC$" role="2OqNvi">
                <ref role="3Tt5mk" to="tpee:gyVODHa" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="6B2Ha6FnWQY" role="3cqZAp" />
        <node concept="3cpWs8" id="6B2Ha6FnWhM" role="3cqZAp">
          <node concept="3cpWsn" id="6B2Ha6FnWhP" role="3cpWs9">
            <property role="TrG5h" value="propertyIndex" />
            <node concept="10Oyi0" id="6B2Ha6FnWhK" role="1tU5fm" />
            <node concept="3cmrfG" id="57NWWvUca3x" role="33vP2m">
              <property role="3cmrfH" value="0" />
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="6B2Ha6FnWkT" role="3cqZAp">
          <node concept="3cpWsn" id="6B2Ha6FnWkW" role="3cpWs9">
            <property role="TrG5h" value="childIndex" />
            <node concept="10Oyi0" id="6B2Ha6FnWkR" role="1tU5fm" />
            <node concept="3cmrfG" id="57NWWvUca68" role="33vP2m">
              <property role="3cmrfH" value="0" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="6B2Ha6FnWn5" role="3cqZAp" />
        <node concept="1DcWWT" id="6B2Ha6FnWqL" role="3cqZAp">
          <node concept="3clFbS" id="6B2Ha6FnWqN" role="2LFqv$">
            <node concept="3clFbJ" id="57NWWvUdb1o" role="3cqZAp">
              <node concept="3clFbS" id="57NWWvUdb1q" role="3clFbx">
                <node concept="3clFbF" id="57NWWvUdbAQ" role="3cqZAp">
                  <node concept="1rXfSq" id="57NWWvUdbAO" role="3clFbG">
                    <ref role="37wK5l" node="57NWWvUd9Zw" resolve="appendSpace" />
                    <node concept="37vLTw" id="57NWWvUdbBR" role="37wK5m">
                      <ref role="3cqZAo" node="6B2Ha6FnXgR" resolve="textGenStatements" />
                    </node>
                  </node>
                </node>
              </node>
              <node concept="22lmx$" id="57NWWvUdboc" role="3clFbw">
                <node concept="3eOSWO" id="57NWWvUdbyu" role="3uHU7w">
                  <node concept="3cmrfG" id="57NWWvUdb$C" role="3uHU7w">
                    <property role="3cmrfH" value="0" />
                  </node>
                  <node concept="37vLTw" id="57NWWvUdbqn" role="3uHU7B">
                    <ref role="3cqZAo" node="6B2Ha6FnWkW" resolve="childIndex" />
                  </node>
                </node>
                <node concept="3eOSWO" id="57NWWvUdbet" role="3uHU7B">
                  <node concept="37vLTw" id="57NWWvUdb7x" role="3uHU7B">
                    <ref role="3cqZAo" node="6B2Ha6FnWhP" resolve="propertyIndex" />
                  </node>
                  <node concept="3cmrfG" id="57NWWvUdbf6" role="3uHU7w">
                    <property role="3cmrfH" value="0" />
                  </node>
                </node>
              </node>
            </node>
            <node concept="3clFbH" id="57NWWvUdaVL" role="3cqZAp" />
            <node concept="3cpWs8" id="6B2Ha6FnWJe" role="3cqZAp">
              <node concept="3cpWsn" id="6B2Ha6FnWJf" role="3cpWs9">
                <property role="TrG5h" value="rule" />
                <node concept="3uibUv" id="Pei6ieUWNe" role="1tU5fm">
                  <ref role="3uigEE" to="kr2q:~Rule" resolve="Rule" />
                </node>
                <node concept="2OqwBi" id="6B2Ha6FnWLn" role="33vP2m">
                  <node concept="37vLTw" id="Pei6ieUWPm" role="2Oq$k0">
                    <ref role="3cqZAo" node="6B2Ha6FnWqO" resolve="element" />
                  </node>
                  <node concept="2OwXpG" id="Pei6ieUWS4" role="2OqNvi">
                    <ref role="2Oxat5" to="kr2q:~RuleReference.rule" resolve="rule" />
                  </node>
                </node>
              </node>
            </node>
            <node concept="3clFbJ" id="6B2Ha6FnWHb" role="3cqZAp">
              <node concept="3clFbS" id="6B2Ha6FnWHd" role="3clFbx">
                <node concept="3SKdUt" id="6B2Ha6FnYVA" role="3cqZAp">
                  <node concept="3SKdUq" id="6B2Ha6FnYVC" role="3SKWNk">
                    <property role="3SKdUp" value="Add constant content" />
                  </node>
                </node>
                <node concept="3clFbF" id="6B2Ha6Fo1ts" role="3cqZAp">
                  <node concept="1rXfSq" id="6B2Ha6Fo1tq" role="3clFbG">
                    <ref role="37wK5l" node="6B2Ha6Fo1$U" resolve="appendStringStatement" />
                    <node concept="37vLTw" id="6B2Ha6Fo1uh" role="37wK5m">
                      <ref role="3cqZAo" node="6B2Ha6FnXgR" resolve="textGenStatements" />
                    </node>
                    <node concept="2OqwBi" id="6B2Ha6Fo1Lo" role="37wK5m">
                      <node concept="1eOMI4" id="6B2Ha6Fo1zJ" role="2Oq$k0">
                        <node concept="10QFUN" id="6B2Ha6Fo1zG" role="1eOMHV">
                          <node concept="3uibUv" id="Pei6ieUZIx" role="10QFUM">
                            <ref role="3uigEE" to="kr2q:~LiteralRule" resolve="LiteralRule" />
                          </node>
                          <node concept="37vLTw" id="6B2Ha6Fo1wg" role="10QFUP">
                            <ref role="3cqZAo" node="6B2Ha6FnWJf" resolve="rule" />
                          </node>
                        </node>
                      </node>
                      <node concept="2OwXpG" id="Pei6ieUZLe" role="2OqNvi">
                        <ref role="2Oxat5" to="kr2q:~LiteralRule.value" resolve="value" />
                      </node>
                    </node>
                  </node>
                </node>
              </node>
              <node concept="2ZW3vV" id="6B2Ha6FnWPG" role="3clFbw">
                <node concept="3uibUv" id="Pei6ieUTPW" role="2ZW6by">
                  <ref role="3uigEE" to="kr2q:~LiteralRule" resolve="LiteralRule" />
                </node>
                <node concept="37vLTw" id="6B2Ha6FnWO_" role="2ZW6bz">
                  <ref role="3cqZAo" node="6B2Ha6FnWJf" resolve="rule" />
                </node>
              </node>
              <node concept="3eNFk2" id="6B2Ha6FnYNx" role="3eNLev">
                <node concept="2ZW3vV" id="6B2Ha6FnYQl" role="3eO9$A">
                  <node concept="3uibUv" id="Pei6ieUZIi" role="2ZW6by">
                    <ref role="3uigEE" to="kr2q:~RegexRule" resolve="RegexRule" />
                  </node>
                  <node concept="37vLTw" id="6B2Ha6FnYOf" role="2ZW6bz">
                    <ref role="3cqZAo" node="6B2Ha6FnWJf" resolve="rule" />
                  </node>
                </node>
                <node concept="3clFbS" id="6B2Ha6FnYNz" role="3eOfB_">
                  <node concept="3SKdUt" id="6B2Ha6FnYVa" role="3cqZAp">
                    <node concept="3SKdUq" id="6B2Ha6FnYVb" role="3SKWNk">
                      <property role="3SKdUp" value="Find property" />
                    </node>
                  </node>
                  <node concept="3cpWs8" id="57NWWvUc5VE" role="3cqZAp">
                    <node concept="3cpWsn" id="57NWWvUc5VK" role="3cpWs9">
                      <property role="TrG5h" value="property" />
                      <node concept="3Tqbb2" id="57NWWvUc5Wl" role="1tU5fm">
                        <ref role="ehGHo" to="tpce:f_TJgxF" resolve="PropertyDeclaration" />
                      </node>
                      <node concept="2YIFZM" id="57NWWvUc897" role="33vP2m">
                        <ref role="37wK5l" node="3pfFnS02Gte" resolve="getProperty" />
                        <ref role="1Pybhc" node="Pei6ieTS8A" resolve="NodeHelper" />
                        <node concept="37vLTw" id="57NWWvUc8aa" role="37wK5m">
                          <ref role="3cqZAo" node="6B2Ha6FnMQx" resolve="node" />
                        </node>
                        <node concept="3uNrnE" id="57NWWvUc8nH" role="37wK5m">
                          <node concept="37vLTw" id="57NWWvUc8nJ" role="2$L3a6">
                            <ref role="3cqZAo" node="6B2Ha6FnWhP" resolve="propertyIndex" />
                          </node>
                        </node>
                      </node>
                    </node>
                  </node>
                  <node concept="3cpWs8" id="57NWWvUdgWk" role="3cqZAp">
                    <node concept="3cpWsn" id="57NWWvUdgWl" role="3cpWs9">
                      <property role="TrG5h" value="paramAccess" />
                      <node concept="3Tqbb2" id="57NWWvUdgWm" role="1tU5fm">
                        <ref role="ehGHo" to="tpee:g76ryKb" resolve="ConceptFunctionParameter" />
                      </node>
                      <node concept="2OqwBi" id="57NWWvUdgWn" role="33vP2m">
                        <node concept="2OqwBi" id="57NWWvUdgWo" role="2Oq$k0">
                          <node concept="2OqwBi" id="57NWWvUdgWp" role="2Oq$k0">
                            <node concept="2OqwBi" id="57NWWvUdgWq" role="2Oq$k0">
                              <node concept="37vLTw" id="57NWWvUdgWr" role="2Oq$k0">
                                <ref role="3cqZAo" node="6B2Ha6FnMOi" resolve="textGen" />
                              </node>
                              <node concept="3TrEf2" id="57NWWvUdgWs" role="2OqNvi">
                                <ref role="3Tt5mk" to="2omo:hX1c4lS" />
                              </node>
                            </node>
                            <node concept="2qgKlT" id="57NWWvUdgWt" role="2OqNvi">
                              <ref role="37wK5l" to="tpek:hEwIGRM" resolve="getParameters" />
                            </node>
                          </node>
                          <node concept="34jXtK" id="57NWWvUdgWu" role="2OqNvi">
                            <node concept="3cmrfG" id="57NWWvUdgWv" role="25WWJ7">
                              <property role="3cmrfH" value="2" />
                            </node>
                          </node>
                        </node>
                        <node concept="LFhST" id="57NWWvUdgWw" role="2OqNvi" />
                      </node>
                    </node>
                  </node>
                  <node concept="3cpWs8" id="57NWWvUcw1c" role="3cqZAp">
                    <node concept="3cpWsn" id="57NWWvUcw1d" role="3cpWs9">
                      <property role="TrG5h" value="propertyAccess" />
                      <node concept="3Tqbb2" id="57NWWvUcw1e" role="1tU5fm">
                        <ref role="ehGHo" to="tp25:gzTrcDJ" resolve="SPropertyAccess" />
                      </node>
                      <node concept="2ShNRf" id="57NWWvUcw1f" role="33vP2m">
                        <node concept="3zrR0B" id="57NWWvUcw1g" role="2ShVmc">
                          <node concept="3Tqbb2" id="57NWWvUcw1h" role="3zrR0E">
                            <ref role="ehGHo" to="tp25:gzTrcDJ" resolve="SPropertyAccess" />
                          </node>
                        </node>
                      </node>
                    </node>
                  </node>
                  <node concept="3clFbF" id="57NWWvUcw1i" role="3cqZAp">
                    <node concept="37vLTI" id="57NWWvUcw1j" role="3clFbG">
                      <node concept="37vLTw" id="57NWWvUcw1k" role="37vLTx">
                        <ref role="3cqZAo" node="57NWWvUc5VK" resolve="property" />
                      </node>
                      <node concept="2OqwBi" id="57NWWvUcw1l" role="37vLTJ">
                        <node concept="37vLTw" id="57NWWvUcw1m" role="2Oq$k0">
                          <ref role="3cqZAo" node="57NWWvUcw1d" resolve="propertyAccess" />
                        </node>
                        <node concept="3TrEf2" id="57NWWvUcw1n" role="2OqNvi">
                          <ref role="3Tt5mk" to="tp25:gzTsBJd" />
                        </node>
                      </node>
                    </node>
                  </node>
                  <node concept="3clFbH" id="57NWWvUdhhz" role="3cqZAp" />
                  <node concept="3clFbF" id="57NWWvUcw1F" role="3cqZAp">
                    <node concept="2OqwBi" id="57NWWvUcw1G" role="3clFbG">
                      <node concept="2OqwBi" id="57NWWvUcw1H" role="2Oq$k0">
                        <node concept="3Tsc0h" id="57NWWvUcw1J" role="2OqNvi">
                          <ref role="3TtcxE" to="tpee:fzcqZ_x" />
                        </node>
                        <node concept="37vLTw" id="57NWWvUcwvG" role="2Oq$k0">
                          <ref role="3cqZAo" node="6B2Ha6FnXgR" resolve="textGenStatements" />
                        </node>
                      </node>
                      <node concept="TSZUe" id="57NWWvUcw1K" role="2OqNvi">
                        <node concept="2c44tf" id="57NWWvUcJkR" role="25WWJ7">
                          <node concept="lc7rE" id="57NWWvUcJsH" role="2c44tc">
                            <node concept="l9hG8" id="57NWWvUcJ$A" role="lcghm">
                              <node concept="2OqwBi" id="57NWWvUcJLr" role="lb14g">
                                <node concept="1B$H19" id="57NWWvUcJLw" role="2OqNvi">
                                  <node concept="2c44te" id="57NWWvUcJUv" role="lGtFl">
                                    <node concept="37vLTw" id="57NWWvUcJWe" role="2c44t1">
                                      <ref role="3cqZAo" node="57NWWvUcw1d" resolve="propertyAccess" />
                                    </node>
                                  </node>
                                </node>
                                <node concept="33vP2n" id="57NWWvUcOyp" role="2Oq$k0">
                                  <node concept="2c44te" id="57NWWvUcOzv" role="lGtFl">
                                    <node concept="37vLTw" id="57NWWvUdm69" role="2c44t1">
                                      <ref role="3cqZAo" node="57NWWvUdgWl" resolve="paramAccess" />
                                    </node>
                                  </node>
                                </node>
                              </node>
                            </node>
                          </node>
                        </node>
                      </node>
                    </node>
                  </node>
                  <node concept="3clFbH" id="57NWWvUdhdS" role="3cqZAp" />
                </node>
              </node>
              <node concept="3eNFk2" id="6B2Ha6FnYQX" role="3eNLev">
                <node concept="2ZW3vV" id="6B2Ha6FnYTM" role="3eO9$A">
                  <node concept="3uibUv" id="Pei6ieUTQb" role="2ZW6by">
                    <ref role="3uigEE" to="kr2q:~ParserRule" resolve="ParserRule" />
                  </node>
                  <node concept="37vLTw" id="6B2Ha6FnYRK" role="2ZW6bz">
                    <ref role="3cqZAo" node="6B2Ha6FnWJf" resolve="rule" />
                  </node>
                </node>
                <node concept="3clFbS" id="6B2Ha6FnYQZ" role="3eOfB_">
                  <node concept="3SKdUt" id="6B2Ha6FnYUI" role="3cqZAp">
                    <node concept="3SKdUq" id="6B2Ha6FnYUJ" role="3SKWNk">
                      <property role="3SKdUp" value="Find child" />
                    </node>
                  </node>
                  <node concept="3cpWs8" id="57NWWvUdcw$" role="3cqZAp">
                    <node concept="3cpWsn" id="57NWWvUdcw_" role="3cpWs9">
                      <property role="TrG5h" value="childLink" />
                      <node concept="3Tqbb2" id="57NWWvUdcwy" role="1tU5fm">
                        <ref role="ehGHo" to="tpce:f_TJgxE" resolve="LinkDeclaration" />
                      </node>
                      <node concept="2YIFZM" id="57NWWvUdcwA" role="33vP2m">
                        <ref role="37wK5l" node="4X6IwqdOs4G" resolve="getChildLink" />
                        <ref role="1Pybhc" node="Pei6ieTS8A" resolve="NodeHelper" />
                        <node concept="37vLTw" id="57NWWvUdcwB" role="37wK5m">
                          <ref role="3cqZAo" node="6B2Ha6FnMQx" resolve="node" />
                        </node>
                        <node concept="3uNrnE" id="57NWWvUdcwC" role="37wK5m">
                          <node concept="37vLTw" id="57NWWvUdcwD" role="2$L3a6">
                            <ref role="3cqZAo" node="6B2Ha6FnWkW" resolve="childIndex" />
                          </node>
                        </node>
                      </node>
                    </node>
                  </node>
                  <node concept="3cpWs8" id="57NWWvUdfGS" role="3cqZAp">
                    <node concept="3cpWsn" id="57NWWvUdfGT" role="3cpWs9">
                      <property role="TrG5h" value="paramAccess" />
                      <node concept="3Tqbb2" id="57NWWvUdfGx" role="1tU5fm">
                        <ref role="ehGHo" to="tpee:g76ryKb" resolve="ConceptFunctionParameter" />
                      </node>
                      <node concept="2OqwBi" id="57NWWvUdfGU" role="33vP2m">
                        <node concept="2OqwBi" id="57NWWvUdfGV" role="2Oq$k0">
                          <node concept="2OqwBi" id="57NWWvUdgx0" role="2Oq$k0">
                            <node concept="2OqwBi" id="57NWWvUdg22" role="2Oq$k0">
                              <node concept="37vLTw" id="57NWWvUdfVK" role="2Oq$k0">
                                <ref role="3cqZAo" node="6B2Ha6FnMOi" resolve="textGen" />
                              </node>
                              <node concept="3TrEf2" id="57NWWvUdgiY" role="2OqNvi">
                                <ref role="3Tt5mk" to="2omo:hX1c4lS" />
                              </node>
                            </node>
                            <node concept="2qgKlT" id="57NWWvUdgQz" role="2OqNvi">
                              <ref role="37wK5l" to="tpek:hEwIGRM" resolve="getParameters" />
                            </node>
                          </node>
                          <node concept="34jXtK" id="57NWWvUdfGX" role="2OqNvi">
                            <node concept="3cmrfG" id="6$MHGUSb8KO" role="25WWJ7">
                              <property role="3cmrfH" value="2" />
                            </node>
                          </node>
                        </node>
                        <node concept="LFhST" id="57NWWvUdfGZ" role="2OqNvi" />
                      </node>
                    </node>
                  </node>
                  <node concept="3clFbH" id="57NWWvUdbQI" role="3cqZAp" />
                  <node concept="3clFbJ" id="57NWWvUdhlA" role="3cqZAp">
                    <node concept="3clFbS" id="57NWWvUdhlC" role="3clFbx">
                      <node concept="3SKdUt" id="57NWWvUdtV8" role="3cqZAp">
                        <node concept="3SKdUq" id="57NWWvUdtVa" role="3SKWNk">
                          <property role="3SKdUp" value="Single child" />
                        </node>
                      </node>
                      <node concept="3cpWs8" id="57NWWvUdbQJ" role="3cqZAp">
                        <node concept="3cpWsn" id="57NWWvUdbQK" role="3cpWs9">
                          <property role="TrG5h" value="childAccess" />
                          <node concept="3Tqbb2" id="57NWWvUdbQL" role="1tU5fm">
                            <ref role="ehGHo" to="tp25:gzTrEba" resolve="SLinkAccess" />
                          </node>
                          <node concept="2ShNRf" id="57NWWvUdbQM" role="33vP2m">
                            <node concept="3zrR0B" id="57NWWvUdbQN" role="2ShVmc">
                              <node concept="3Tqbb2" id="57NWWvUdbQO" role="3zrR0E">
                                <ref role="ehGHo" to="tp25:gzTrEba" resolve="SLinkAccess" />
                              </node>
                            </node>
                          </node>
                        </node>
                      </node>
                      <node concept="3clFbF" id="57NWWvUdjCH" role="3cqZAp">
                        <node concept="37vLTI" id="57NWWvUdjPG" role="3clFbG">
                          <node concept="37vLTw" id="57NWWvUdjY5" role="37vLTx">
                            <ref role="3cqZAo" node="57NWWvUdcw_" resolve="childLink" />
                          </node>
                          <node concept="2OqwBi" id="57NWWvUdjFf" role="37vLTJ">
                            <node concept="37vLTw" id="57NWWvUdjCF" role="2Oq$k0">
                              <ref role="3cqZAo" node="57NWWvUdbQK" resolve="childAccess" />
                            </node>
                            <node concept="3TrEf2" id="57NWWvUdxPd" role="2OqNvi">
                              <ref role="3Tt5mk" to="tp25:gzTt5is" />
                            </node>
                          </node>
                        </node>
                      </node>
                      <node concept="3clFbF" id="57NWWvUdbQV" role="3cqZAp">
                        <node concept="2OqwBi" id="57NWWvUdbQW" role="3clFbG">
                          <node concept="2OqwBi" id="57NWWvUdbQX" role="2Oq$k0">
                            <node concept="3Tsc0h" id="57NWWvUdbQY" role="2OqNvi">
                              <ref role="3TtcxE" to="tpee:fzcqZ_x" />
                            </node>
                            <node concept="37vLTw" id="57NWWvUdbQZ" role="2Oq$k0">
                              <ref role="3cqZAo" node="6B2Ha6FnXgR" resolve="textGenStatements" />
                            </node>
                          </node>
                          <node concept="TSZUe" id="57NWWvUdbR0" role="2OqNvi">
                            <node concept="2c44tf" id="57NWWvUdbR1" role="25WWJ7">
                              <node concept="lc7rE" id="57NWWvUdbR2" role="2c44tc">
                                <node concept="l9hG8" id="57NWWvUdbR3" role="lcghm">
                                  <node concept="2OqwBi" id="57NWWvUdbR4" role="lb14g">
                                    <node concept="1B$H19" id="57NWWvUdbR5" role="2OqNvi">
                                      <node concept="2c44te" id="57NWWvUdbR6" role="lGtFl">
                                        <node concept="37vLTw" id="57NWWvUdbR7" role="2c44t1">
                                          <ref role="3cqZAo" node="57NWWvUdbQK" resolve="childAccess" />
                                        </node>
                                      </node>
                                    </node>
                                    <node concept="33vP2n" id="57NWWvUdbR8" role="2Oq$k0">
                                      <node concept="2c44te" id="57NWWvUdbR9" role="lGtFl">
                                        <node concept="37vLTw" id="57NWWvUdfH0" role="2c44t1">
                                          <ref role="3cqZAo" node="57NWWvUdfGT" resolve="paramAccess" />
                                        </node>
                                      </node>
                                    </node>
                                  </node>
                                </node>
                              </node>
                            </node>
                          </node>
                        </node>
                      </node>
                      <node concept="3clFbH" id="57NWWvUdhlB" role="3cqZAp" />
                    </node>
                    <node concept="9aQIb" id="57NWWvUdjej" role="9aQIa">
                      <node concept="3clFbS" id="57NWWvUdjek" role="9aQI4">
                        <node concept="3SKdUt" id="57NWWvUdtTI" role="3cqZAp">
                          <node concept="3SKdUq" id="57NWWvUdtTK" role="3SKWNk">
                            <property role="3SKdUp" value="List of children" />
                          </node>
                        </node>
                        <node concept="3cpWs8" id="57NWWvUdk8A" role="3cqZAp">
                          <node concept="3cpWsn" id="57NWWvUdk8B" role="3cpWs9">
                            <property role="TrG5h" value="childAccess" />
                            <node concept="3Tqbb2" id="57NWWvUdk8C" role="1tU5fm">
                              <ref role="ehGHo" to="tp25:gzTsc4p" resolve="SLinkListAccess" />
                            </node>
                            <node concept="2ShNRf" id="57NWWvUdk8D" role="33vP2m">
                              <node concept="3zrR0B" id="57NWWvUdk8E" role="2ShVmc">
                                <node concept="3Tqbb2" id="57NWWvUdk8F" role="3zrR0E">
                                  <ref role="ehGHo" to="tp25:gzTsc4p" resolve="SLinkListAccess" />
                                </node>
                              </node>
                            </node>
                          </node>
                        </node>
                        <node concept="3clFbF" id="57NWWvUdk8G" role="3cqZAp">
                          <node concept="37vLTI" id="57NWWvUdk8H" role="3clFbG">
                            <node concept="37vLTw" id="57NWWvUdk8I" role="37vLTx">
                              <ref role="3cqZAo" node="57NWWvUdcw_" resolve="childLink" />
                            </node>
                            <node concept="2OqwBi" id="57NWWvUdxTE" role="37vLTJ">
                              <node concept="37vLTw" id="57NWWvUdk8K" role="2Oq$k0">
                                <ref role="3cqZAo" node="57NWWvUdk8B" resolve="childAccess" />
                              </node>
                              <node concept="3TrEf2" id="57NWWvUdxZw" role="2OqNvi">
                                <ref role="3Tt5mk" to="tp25:gzTtc_y" />
                              </node>
                            </node>
                          </node>
                        </node>
                        <node concept="3clFbF" id="57NWWvUdk8M" role="3cqZAp">
                          <node concept="2OqwBi" id="57NWWvUdk8N" role="3clFbG">
                            <node concept="2OqwBi" id="57NWWvUdk8O" role="2Oq$k0">
                              <node concept="3Tsc0h" id="57NWWvUdk8P" role="2OqNvi">
                                <ref role="3TtcxE" to="tpee:fzcqZ_x" />
                              </node>
                              <node concept="37vLTw" id="57NWWvUdk8Q" role="2Oq$k0">
                                <ref role="3cqZAo" node="6B2Ha6FnXgR" resolve="textGenStatements" />
                              </node>
                            </node>
                            <node concept="TSZUe" id="57NWWvUdk8R" role="2OqNvi">
                              <node concept="2c44tf" id="57NWWvUdk8S" role="25WWJ7">
                                <node concept="lc7rE" id="57NWWvUdk8T" role="2c44tc">
                                  <node concept="l9S2W" id="57NWWvUdkun" role="lcghm">
                                    <node concept="2OqwBi" id="57NWWvUdkD8" role="lbANJ">
                                      <node concept="1B$H19" id="57NWWvUdkLg" role="2OqNvi">
                                        <node concept="2c44te" id="57NWWvUdkLQ" role="lGtFl">
                                          <node concept="37vLTw" id="57NWWvUdkMK" role="2c44t1">
                                            <ref role="3cqZAo" node="57NWWvUdk8B" resolve="childAccess" />
                                          </node>
                                        </node>
                                      </node>
                                      <node concept="33vP2n" id="57NWWvUdkNf" role="2Oq$k0">
                                        <node concept="2c44te" id="57NWWvUdkNO" role="lGtFl">
                                          <node concept="37vLTw" id="57NWWvUdkOG" role="2c44t1">
                                            <ref role="3cqZAo" node="57NWWvUdfGT" resolve="paramAccess" />
                                          </node>
                                        </node>
                                      </node>
                                    </node>
                                  </node>
                                </node>
                              </node>
                            </node>
                          </node>
                        </node>
                      </node>
                    </node>
                    <node concept="22lmx$" id="57NWWvUdFzs" role="3clFbw">
                      <node concept="3clFbC" id="57NWWvUdFHs" role="3uHU7w">
                        <node concept="Rm8GO" id="57NWWvUdFOr" role="3uHU7w">
                          <ref role="Rm8GQ" to="kr2q:~Quantity.MAX_ONE" resolve="MAX_ONE" />
                          <ref role="1Px2BO" to="kr2q:~Quantity" resolve="Quantity" />
                        </node>
                        <node concept="2OqwBi" id="57NWWvUdFAt" role="3uHU7B">
                          <node concept="37vLTw" id="57NWWvUdF_7" role="2Oq$k0">
                            <ref role="3cqZAo" node="6B2Ha6FnWqO" resolve="element" />
                          </node>
                          <node concept="2OwXpG" id="57NWWvUdFDr" role="2OqNvi">
                            <ref role="2Oxat5" to="kr2q:~RuleReference.quantity" resolve="quantity" />
                          </node>
                        </node>
                      </node>
                      <node concept="3clFbC" id="57NWWvUdFsF" role="3uHU7B">
                        <node concept="2OqwBi" id="57NWWvUdFnV" role="3uHU7B">
                          <node concept="37vLTw" id="57NWWvUdFn4" role="2Oq$k0">
                            <ref role="3cqZAo" node="6B2Ha6FnWqO" resolve="element" />
                          </node>
                          <node concept="2OwXpG" id="57NWWvUdFpb" role="2OqNvi">
                            <ref role="2Oxat5" to="kr2q:~RuleReference.quantity" resolve="quantity" />
                          </node>
                        </node>
                        <node concept="Rm8GO" id="57NWWvUdFvi" role="3uHU7w">
                          <ref role="Rm8GQ" to="kr2q:~Quantity.EXACTLY_ONE" resolve="EXACTLY_ONE" />
                          <ref role="1Px2BO" to="kr2q:~Quantity" resolve="Quantity" />
                        </node>
                      </node>
                    </node>
                  </node>
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWsn" id="6B2Ha6FnWqO" role="1Duv9x">
            <property role="TrG5h" value="element" />
            <node concept="3uibUv" id="Pei6ieUWFB" role="1tU5fm">
              <ref role="3uigEE" to="kr2q:~RuleReference" resolve="RuleReference" />
            </node>
          </node>
          <node concept="37vLTw" id="6B2Ha6FnWxU" role="1DdaDG">
            <ref role="3cqZAo" node="6B2Ha6FnN78" resolve="elements" />
          </node>
        </node>
        <node concept="3clFbH" id="6B2Ha6FnWtq" role="3cqZAp" />
        <node concept="3cpWs6" id="6B2Ha6FnSc1" role="3cqZAp">
          <node concept="37vLTw" id="6B2Ha6FnSlq" role="3cqZAk">
            <ref role="3cqZAo" node="6B2Ha6FnMOi" resolve="textGen" />
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="6B2Ha6FnMLX" role="1B3o_S" />
      <node concept="3Tqbb2" id="6B2Ha6FnMOe" role="3clF45">
        <ref role="ehGHo" to="2omo:hWWtQdD" resolve="ConceptTextGenDeclaration" />
      </node>
      <node concept="37vLTG" id="6B2Ha6FnMQx" role="3clF46">
        <property role="TrG5h" value="node" />
        <node concept="3Tqbb2" id="6B2Ha6FnMQw" role="1tU5fm">
          <ref role="ehGHo" to="tpce:f_TIwhg" resolve="ConceptDeclaration" />
        </node>
      </node>
      <node concept="37vLTG" id="6B2Ha6FnN78" role="3clF46">
        <property role="TrG5h" value="elements" />
        <node concept="3uibUv" id="6B2Ha6FnVw7" role="1tU5fm">
          <ref role="3uigEE" to="33ny:~List" resolve="List" />
          <node concept="3uibUv" id="Pei6ieV2At" role="11_B2D">
            <ref role="3uigEE" to="kr2q:~RuleReference" resolve="RuleReference" />
          </node>
        </node>
      </node>
      <node concept="P$JXv" id="6B2Ha6FnYK5" role="lGtFl">
        <node concept="TZ5HA" id="6B2Ha6FnYK6" role="TZ5H$">
          <node concept="1dT_AC" id="6B2Ha6FnYK7" role="1dT_Ay">
            <property role="1dT_AB" value="Builds the TextGen aspect for a given node, following structure from original alternative." />
          </node>
        </node>
      </node>
    </node>
    <node concept="2tJIrI" id="57NWWvUd9JP" role="jymVt" />
    <node concept="2YIFZL" id="57NWWvUd9Zw" role="jymVt">
      <property role="TrG5h" value="appendSpace" />
      <property role="od$2w" value="false" />
      <property role="DiZV1" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="57NWWvUd9Zz" role="3clF47">
        <node concept="3clFbF" id="57NWWvUdabL" role="3cqZAp">
          <node concept="1rXfSq" id="57NWWvUdabK" role="3clFbG">
            <ref role="37wK5l" node="6B2Ha6Fo1$U" resolve="appendStringStatement" />
            <node concept="37vLTw" id="57NWWvUdacK" role="37wK5m">
              <ref role="3cqZAo" node="57NWWvUda2I" resolve="statements" />
            </node>
            <node concept="Xl_RD" id="57NWWvUdadO" role="37wK5m">
              <property role="Xl_RC" value=" " />
            </node>
          </node>
        </node>
      </node>
      <node concept="3Tm6S6" id="57NWWvUd9Q2" role="1B3o_S" />
      <node concept="3cqZAl" id="57NWWvUd9Zu" role="3clF45" />
      <node concept="37vLTG" id="57NWWvUda2I" role="3clF46">
        <property role="TrG5h" value="statements" />
        <node concept="3Tqbb2" id="57NWWvUda2H" role="1tU5fm">
          <ref role="ehGHo" to="tpee:fzclF80" resolve="StatementList" />
        </node>
      </node>
    </node>
    <node concept="2tJIrI" id="6B2Ha6Fo1$T" role="jymVt" />
    <node concept="2YIFZL" id="6B2Ha6Fo1$U" role="jymVt">
      <property role="TrG5h" value="appendStringStatement" />
      <property role="od$2w" value="false" />
      <property role="DiZV1" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="6B2Ha6Fo1$V" role="3clF47">
        <node concept="3cpWs8" id="6B2Ha6Fo8EV" role="3cqZAp">
          <node concept="3cpWsn" id="6B2Ha6Fo8F1" role="3cpWs9">
            <property role="TrG5h" value="constant" />
            <node concept="3Tqbb2" id="6B2Ha6Fo8Fv" role="1tU5fm">
              <ref role="ehGHo" to="2omo:i0la8a6" resolve="ConstantStringAppendPart" />
            </node>
            <node concept="2ShNRf" id="6B2Ha6Fo8GO" role="33vP2m">
              <node concept="3zrR0B" id="6B2Ha6Fo8GM" role="2ShVmc">
                <node concept="3Tqbb2" id="6B2Ha6Fo8GN" role="3zrR0E">
                  <ref role="ehGHo" to="2omo:i0la8a6" resolve="ConstantStringAppendPart" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="57NWWvUd2mw" role="3cqZAp">
          <node concept="37vLTI" id="57NWWvUd2tC" role="3clFbG">
            <node concept="37vLTw" id="57NWWvUd2v4" role="37vLTx">
              <ref role="3cqZAo" node="6B2Ha6Fo1_0" resolve="value" />
            </node>
            <node concept="2OqwBi" id="57NWWvUd2oj" role="37vLTJ">
              <node concept="37vLTw" id="57NWWvUd2mu" role="2Oq$k0">
                <ref role="3cqZAo" node="6B2Ha6Fo8F1" resolve="constant" />
              </node>
              <node concept="3TrcHB" id="57NWWvUd2s5" role="2OqNvi">
                <ref role="3TsBF5" to="2omo:i0lacEG" resolve="value" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="6B2Ha6Fo1VH" role="3cqZAp">
          <node concept="2OqwBi" id="6B2Ha6Fo35u" role="3clFbG">
            <node concept="2OqwBi" id="6B2Ha6Fo1ZQ" role="2Oq$k0">
              <node concept="37vLTw" id="6B2Ha6Fo1VF" role="2Oq$k0">
                <ref role="3cqZAo" node="6B2Ha6Fo1$Y" resolve="statements" />
              </node>
              <node concept="3Tsc0h" id="6B2Ha6Fo2jq" role="2OqNvi">
                <ref role="3TtcxE" to="tpee:fzcqZ_x" />
              </node>
            </node>
            <node concept="TSZUe" id="6B2Ha6Fo5q1" role="2OqNvi">
              <node concept="2c44tf" id="57NWWvUcTFt" role="25WWJ7">
                <node concept="lc7rE" id="57NWWvUcTNj" role="2c44tc">
                  <node concept="la8eA" id="57NWWvUcTVc" role="lcghm">
                    <node concept="2c44te" id="57NWWvUcU3y" role="lGtFl">
                      <node concept="37vLTw" id="57NWWvUcU5q" role="2c44t1">
                        <ref role="3cqZAo" node="6B2Ha6Fo8F1" resolve="constant" />
                      </node>
                    </node>
                  </node>
                </node>
              </node>
            </node>
          </node>
        </node>
      </node>
      <node concept="3Tm6S6" id="6B2Ha6Fo1$W" role="1B3o_S" />
      <node concept="37vLTG" id="6B2Ha6Fo1$Y" role="3clF46">
        <property role="TrG5h" value="statements" />
        <node concept="3Tqbb2" id="6B2Ha6Fo1$Z" role="1tU5fm">
          <ref role="ehGHo" to="tpee:fzclF80" resolve="StatementList" />
        </node>
      </node>
      <node concept="37vLTG" id="6B2Ha6Fo1_0" role="3clF46">
        <property role="TrG5h" value="value" />
        <node concept="17QB3L" id="6B2Ha6FofuO" role="1tU5fm" />
      </node>
      <node concept="P$JXv" id="6B2Ha6FofyZ" role="lGtFl">
        <node concept="TZ5HA" id="6B2Ha6Fofz0" role="TZ5H$">
          <node concept="1dT_AC" id="6B2Ha6Fofz1" role="1dT_Ay">
            <property role="1dT_AB" value="Creates an append statement that generates given string." />
          </node>
        </node>
      </node>
      <node concept="3cqZAl" id="6B2Ha6Fomup" role="3clF45" />
    </node>
    <node concept="3Tm1VV" id="Pei6ieTWbX" role="1B3o_S" />
    <node concept="3UR2Jj" id="Pei6ieTWF6" role="lGtFl">
      <node concept="TZ5HA" id="Pei6ieTWF7" role="TZ5H$">
        <node concept="1dT_AC" id="Pei6ieTWF8" role="1dT_Ay">
          <property role="1dT_AB" value="Helper for generating TextGen aspects." />
        </node>
      </node>
    </node>
  </node>
  <node concept="Qs71p" id="Pei6ieU44m">
    <property role="TrG5h" value="Property" />
    <node concept="3Tm1VV" id="Pei6ieU44n" role="1B3o_S" />
    <node concept="3UR2Jj" id="Pei6ieU45e" role="lGtFl">
      <node concept="TZ5HA" id="Pei6ieU45f" role="TZ5H$">
        <node concept="1dT_AC" id="Pei6ieU45g" role="1dT_Ay">
          <property role="1dT_AB" value="Enum used for targeting properties." />
        </node>
      </node>
    </node>
    <node concept="QsSxf" id="Pei6ieU45y" role="Qtgdg">
      <property role="TrG5h" value="Alias" />
      <ref role="37wK5l" to="wyt6:~Object.&lt;init&gt;()" resolve="Object" />
    </node>
    <node concept="QsSxf" id="Pei6ieU45K" role="Qtgdg">
      <property role="TrG5h" value="Name" />
      <ref role="37wK5l" to="wyt6:~Object.&lt;init&gt;()" resolve="Object" />
    </node>
    <node concept="QsSxf" id="Pei6ieU45W" role="Qtgdg">
      <property role="TrG5h" value="VirtualPackage" />
      <ref role="37wK5l" to="wyt6:~Object.&lt;init&gt;()" resolve="Object" />
    </node>
  </node>
</model>
