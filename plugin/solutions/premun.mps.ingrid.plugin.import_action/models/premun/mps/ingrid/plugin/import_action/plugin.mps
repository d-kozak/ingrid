<?xml version="1.0" encoding="UTF-8"?>
<model ref="r:8f990077-2eeb-4671-b5d0-013775b6413e(premun.mps.ingrid.plugin.import_action.plugin)">
  <persistence version="9" />
  <languages>
    <use id="28f9e497-3b42-4291-aeba-0a1039153ab1" name="jetbrains.mps.lang.plugin" version="0" />
    <use id="ef7bf5ac-d06c-4342-b11d-e42104eb9343" name="jetbrains.mps.lang.plugin.standalone" version="0" />
    <use id="7866978e-a0f0-4cc7-81bc-4d213d9375e1" name="jetbrains.mps.lang.smodel" version="2" />
    <use id="f3061a53-9226-4cc5-a443-f952ceaf5816" name="jetbrains.mps.baseLanguage" version="4" />
  </languages>
  <imports>
    <import index="guwi" ref="6354ebe7-c22a-4a0f-ac54-50b52ab9b065/java:java.io(JDK/)" />
    <import index="z1c3" ref="6ed54515-acc8-4d1e-a16c-9fd6cfe951ea/java:jetbrains.mps.project(MPS.Core/)" />
    <import index="w1kc" ref="6ed54515-acc8-4d1e-a16c-9fd6cfe951ea/java:jetbrains.mps.smodel(MPS.Core/)" />
    <import index="qq03" ref="742f6602-5a2f-4313-aa6e-ae1cd4ffdc61/java:jetbrains.mps.ide.actions(MPS.Platform/)" />
    <import index="dxuu" ref="6354ebe7-c22a-4a0f-ac54-50b52ab9b065/java:javax.swing(JDK/)" />
    <import index="lui2" ref="8865b7a8-5271-43d3-884c-6fd1d9cfdd34/java:org.jetbrains.mps.openapi.module(MPS.OpenAPI/)" />
    <import index="mhbf" ref="8865b7a8-5271-43d3-884c-6fd1d9cfdd34/java:org.jetbrains.mps.openapi.model(MPS.OpenAPI/)" />
    <import index="zkjw" ref="8f833572-184a-4705-be72-ff2712986984/java:premun.mps.ingrid.importer(premun.mps.ingrid.plugin.import_action/)" />
    <import index="z60i" ref="6354ebe7-c22a-4a0f-ac54-50b52ab9b065/java:java.awt(JDK/)" />
    <import index="wyt6" ref="6354ebe7-c22a-4a0f-ac54-50b52ab9b065/java:java.lang(JDK/)" implicit="true" />
    <import index="tprs" ref="r:00000000-0000-4000-0000-011c895904a4(jetbrains.mps.ide.actions)" implicit="true" />
  </imports>
  <registry>
    <language id="28f9e497-3b42-4291-aeba-0a1039153ab1" name="jetbrains.mps.lang.plugin">
      <concept id="1207145163717" name="jetbrains.mps.lang.plugin.structure.ElementListContents" flags="ng" index="ftmFs">
        <child id="1207145201301" name="reference" index="ftvYc" />
      </concept>
      <concept id="1203071646776" name="jetbrains.mps.lang.plugin.structure.ActionDeclaration" flags="ng" index="sE7Ow">
        <property id="1205250923097" name="caption" index="2uzpH1" />
        <property id="1213273179528" name="description" index="1WHSii" />
        <child id="1203083461638" name="executeFunction" index="tncku" />
        <child id="1205851242421" name="methodDeclaration" index="32lrUH" />
        <child id="1217413222820" name="parameter" index="1NuT2Z" />
      </concept>
      <concept id="1203083511112" name="jetbrains.mps.lang.plugin.structure.ExecuteBlock" flags="in" index="tnohg" />
      <concept id="1203087890642" name="jetbrains.mps.lang.plugin.structure.ActionGroupDeclaration" flags="ng" index="tC5Ba">
        <child id="1204991552650" name="modifier" index="2f5YQi" />
        <child id="1207145245948" name="contents" index="ftER_" />
      </concept>
      <concept id="1203088046679" name="jetbrains.mps.lang.plugin.structure.ActionInstance" flags="ng" index="tCFHf">
        <reference id="1203088061055" name="action" index="tCJdB" />
      </concept>
      <concept id="1203092361741" name="jetbrains.mps.lang.plugin.structure.ModificationStatement" flags="lg" index="tT9cl">
        <reference id="1204992316090" name="point" index="2f8Tey" />
        <reference id="1203092736097" name="modifiedGroup" index="tU$_T" />
      </concept>
      <concept id="1205679047295" name="jetbrains.mps.lang.plugin.structure.ActionParameterDeclaration" flags="ig" index="2S4$dB" />
      <concept id="5538333046911348654" name="jetbrains.mps.lang.plugin.structure.RequiredCondition" flags="ng" index="1oajcY" />
      <concept id="1217252042208" name="jetbrains.mps.lang.plugin.structure.ActionDataParameterDeclaration" flags="ng" index="1DS2jV">
        <reference id="1217252646389" name="key" index="1DUlNI" />
      </concept>
      <concept id="1217252428768" name="jetbrains.mps.lang.plugin.structure.ActionDataParameterReferenceOperation" flags="nn" index="1DTwFV" />
      <concept id="1217413147516" name="jetbrains.mps.lang.plugin.structure.ActionParameter" flags="ng" index="1NuADB">
        <child id="5538333046911298738" name="condition" index="1oa70y" />
      </concept>
    </language>
    <language id="ef7bf5ac-d06c-4342-b11d-e42104eb9343" name="jetbrains.mps.lang.plugin.standalone">
      <concept id="7520713872864775836" name="jetbrains.mps.lang.plugin.standalone.structure.StandalonePluginDescriptor" flags="ng" index="2DaZZR" />
    </language>
    <language id="f3061a53-9226-4cc5-a443-f952ceaf5816" name="jetbrains.mps.baseLanguage">
      <concept id="1215693861676" name="jetbrains.mps.baseLanguage.structure.BaseAssignmentExpression" flags="nn" index="d038R">
        <child id="1068498886297" name="rValue" index="37vLTx" />
        <child id="1068498886295" name="lValue" index="37vLTJ" />
      </concept>
      <concept id="1202948039474" name="jetbrains.mps.baseLanguage.structure.InstanceMethodCallOperation" flags="nn" index="liA8E" />
      <concept id="1154032098014" name="jetbrains.mps.baseLanguage.structure.AbstractLoopStatement" flags="nn" index="2LF5Ji">
        <child id="1154032183016" name="body" index="2LFqv$" />
      </concept>
      <concept id="1197027756228" name="jetbrains.mps.baseLanguage.structure.DotExpression" flags="nn" index="2OqwBi">
        <child id="1197027771414" name="operand" index="2Oq$k0" />
        <child id="1197027833540" name="operation" index="2OqNvi" />
      </concept>
      <concept id="1083260308424" name="jetbrains.mps.baseLanguage.structure.EnumConstantReference" flags="nn" index="Rm8GO">
        <reference id="1083260308426" name="enumConstantDeclaration" index="Rm8GQ" />
        <reference id="1144432896254" name="enumClass" index="1Px2BO" />
      </concept>
      <concept id="1145552977093" name="jetbrains.mps.baseLanguage.structure.GenericNewExpression" flags="nn" index="2ShNRf">
        <child id="1145553007750" name="creator" index="2ShVmc" />
      </concept>
      <concept id="1137021947720" name="jetbrains.mps.baseLanguage.structure.ConceptFunction" flags="in" index="2VMwT0">
        <child id="1137022507850" name="body" index="2VODD2" />
      </concept>
      <concept id="1070475926800" name="jetbrains.mps.baseLanguage.structure.StringLiteral" flags="nn" index="Xl_RD">
        <property id="1070475926801" name="value" index="Xl_RC" />
      </concept>
      <concept id="1081236700937" name="jetbrains.mps.baseLanguage.structure.StaticMethodCall" flags="nn" index="2YIFZM">
        <reference id="1144433194310" name="classConcept" index="1Pybhc" />
      </concept>
      <concept id="1070533707846" name="jetbrains.mps.baseLanguage.structure.StaticFieldReference" flags="nn" index="10M0yZ">
        <reference id="1144433057691" name="classifier" index="1PxDUh" />
      </concept>
      <concept id="1070534058343" name="jetbrains.mps.baseLanguage.structure.NullLiteral" flags="nn" index="10Nm6u" />
      <concept id="1068431474542" name="jetbrains.mps.baseLanguage.structure.VariableDeclaration" flags="ng" index="33uBYm">
        <child id="1068431790190" name="initializer" index="33vP2m" />
      </concept>
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
        <child id="1068580123133" name="returnType" index="3clF45" />
        <child id="1068580123134" name="parameter" index="3clF46" />
        <child id="1068580123135" name="body" index="3clF47" />
      </concept>
      <concept id="1068580123152" name="jetbrains.mps.baseLanguage.structure.EqualsExpression" flags="nn" index="3clFbC" />
      <concept id="1068580123155" name="jetbrains.mps.baseLanguage.structure.ExpressionStatement" flags="nn" index="3clFbF">
        <child id="1068580123156" name="expression" index="3clFbG" />
      </concept>
      <concept id="1068580123157" name="jetbrains.mps.baseLanguage.structure.Statement" flags="nn" index="3clFbH" />
      <concept id="1068580123159" name="jetbrains.mps.baseLanguage.structure.IfStatement" flags="nn" index="3clFbJ">
        <child id="1068580123160" name="condition" index="3clFbw" />
        <child id="1068580123161" name="ifTrue" index="3clFbx" />
      </concept>
      <concept id="1068580123136" name="jetbrains.mps.baseLanguage.structure.StatementList" flags="sn" stub="5293379017992965193" index="3clFbS">
        <child id="1068581517665" name="statement" index="3cqZAp" />
      </concept>
      <concept id="1068580123137" name="jetbrains.mps.baseLanguage.structure.BooleanConstant" flags="nn" index="3clFbT">
        <property id="1068580123138" name="value" index="3clFbU" />
      </concept>
      <concept id="1068581242878" name="jetbrains.mps.baseLanguage.structure.ReturnStatement" flags="nn" index="3cpWs6">
        <child id="1068581517676" name="expression" index="3cqZAk" />
      </concept>
      <concept id="1068581242864" name="jetbrains.mps.baseLanguage.structure.LocalVariableDeclarationStatement" flags="nn" index="3cpWs8">
        <child id="1068581242865" name="localVariableDeclaration" index="3cpWs9" />
      </concept>
      <concept id="1068581242863" name="jetbrains.mps.baseLanguage.structure.LocalVariableDeclaration" flags="nr" index="3cpWsn" />
      <concept id="1068581517677" name="jetbrains.mps.baseLanguage.structure.VoidType" flags="in" index="3cqZAl" />
      <concept id="1204053956946" name="jetbrains.mps.baseLanguage.structure.IMethodCall" flags="ng" index="1ndlxa">
        <reference id="1068499141037" name="baseMethodDeclaration" index="37wK5l" />
        <child id="1068499141038" name="actualArgument" index="37wK5m" />
      </concept>
      <concept id="1212685548494" name="jetbrains.mps.baseLanguage.structure.ClassCreator" flags="nn" index="1pGfFk" />
      <concept id="1171903916106" name="jetbrains.mps.baseLanguage.structure.UpperBoundType" flags="in" index="3qUE_q">
        <child id="1171903916107" name="bound" index="3qUE_r" />
      </concept>
      <concept id="1107535904670" name="jetbrains.mps.baseLanguage.structure.ClassifierType" flags="in" index="3uibUv">
        <reference id="1107535924139" name="classifier" index="3uigEE" />
      </concept>
      <concept id="1081773326031" name="jetbrains.mps.baseLanguage.structure.BinaryOperation" flags="nn" index="3uHJSO">
        <child id="1081773367579" name="rightExpression" index="3uHU7w" />
        <child id="1081773367580" name="leftExpression" index="3uHU7B" />
      </concept>
      <concept id="1081855346303" name="jetbrains.mps.baseLanguage.structure.BreakStatement" flags="nn" index="3zACq4" />
      <concept id="1178549954367" name="jetbrains.mps.baseLanguage.structure.IVisible" flags="ng" index="1B3ioH">
        <child id="1178549979242" name="visibility" index="1B3o_S" />
      </concept>
      <concept id="1146644623116" name="jetbrains.mps.baseLanguage.structure.PrivateVisibility" flags="nn" index="3Tm6S6" />
    </language>
    <language id="443f4c36-fcf5-4eb6-9500-8d06ed259e3e" name="jetbrains.mps.baseLanguage.classifiers">
      <concept id="1205752633985" name="jetbrains.mps.baseLanguage.classifiers.structure.ThisClassifierExpression" flags="nn" index="2WthIp" />
      <concept id="1205756064662" name="jetbrains.mps.baseLanguage.classifiers.structure.IMemberOperation" flags="ng" index="2WEnae">
        <reference id="1205756909548" name="member" index="2WH_rO" />
      </concept>
      <concept id="1205769003971" name="jetbrains.mps.baseLanguage.classifiers.structure.DefaultClassifierMethodDeclaration" flags="ng" index="2XrIbr" />
      <concept id="1205769149993" name="jetbrains.mps.baseLanguage.classifiers.structure.DefaultClassifierMethodCallOperation" flags="nn" index="2XshWL">
        <child id="1205770614681" name="actualArgument" index="2XxRq1" />
      </concept>
    </language>
    <language id="7866978e-a0f0-4cc7-81bc-4d213d9375e1" name="jetbrains.mps.lang.smodel">
      <concept id="1138055754698" name="jetbrains.mps.lang.smodel.structure.SNodeType" flags="in" index="3Tqbb2" />
    </language>
    <language id="ceab5195-25ea-4f22-9b92-103b95ca8c0c" name="jetbrains.mps.lang.core">
      <concept id="1133920641626" name="jetbrains.mps.lang.core.structure.BaseConcept" flags="ng" index="2VYdi">
        <child id="5169995583184591170" name="smodelAttribute" index="lGtFl" />
      </concept>
      <concept id="1169194658468" name="jetbrains.mps.lang.core.structure.INamedConcept" flags="ng" index="TrEIO">
        <property id="1169194664001" name="name" index="TrG5h" />
      </concept>
      <concept id="709746936026466394" name="jetbrains.mps.lang.core.structure.ChildAttribute" flags="ng" index="3VBwX9">
        <property id="709746936026609031" name="linkId" index="3V$3ak" />
        <property id="709746936026609029" name="linkRole" index="3V$3am" />
      </concept>
      <concept id="4452961908202556907" name="jetbrains.mps.lang.core.structure.BaseCommentAttribute" flags="ng" index="1X3_iC">
        <child id="3078666699043039389" name="commentedNode" index="8Wnug" />
      </concept>
    </language>
    <language id="83888646-71ce-4f1c-9c53-c54016f6ad4f" name="jetbrains.mps.baseLanguage.collections">
      <concept id="1151689724996" name="jetbrains.mps.baseLanguage.collections.structure.SequenceType" flags="in" index="A3Dl8">
        <child id="1151689745422" name="elementType" index="A3Ik2" />
      </concept>
      <concept id="1153943597977" name="jetbrains.mps.baseLanguage.collections.structure.ForEachStatement" flags="nn" index="2Gpval">
        <child id="1153944400369" name="variable" index="2Gsz3X" />
        <child id="1153944424730" name="inputSequence" index="2GsD0m" />
      </concept>
      <concept id="1153944193378" name="jetbrains.mps.baseLanguage.collections.structure.ForEachVariable" flags="nr" index="2GrKxI" />
      <concept id="1153944233411" name="jetbrains.mps.baseLanguage.collections.structure.ForEachVariableReference" flags="nn" index="2GrUjf">
        <reference id="1153944258490" name="variable" index="2Gs0qQ" />
      </concept>
      <concept id="4611582986551314327" name="jetbrains.mps.baseLanguage.collections.structure.OfTypeOperation" flags="nn" index="UnYns">
        <child id="4611582986551314344" name="requestedType" index="UnYnz" />
      </concept>
    </language>
  </registry>
  <node concept="2DaZZR" id="7u4wmVSPxcW" />
  <node concept="sE7Ow" id="3_5Fm_0LVav">
    <property role="TrG5h" value="ImportAction" />
    <property role="2uzpH1" value="Import Grammar" />
    <property role="1WHSii" value="Imports selected grammar into an MPS language" />
    <node concept="2XrIbr" id="4F6jbA$ln8h" role="32lrUH">
      <property role="TrG5h" value="showAlert" />
      <node concept="3clFbS" id="4F6jbA$ln8i" role="3clF47">
        <node concept="3clFbF" id="4F6jbA$lnzj" role="3cqZAp">
          <node concept="2YIFZM" id="4F6jbA$lnz_" role="3clFbG">
            <ref role="1Pybhc" to="dxuu:~JOptionPane" resolve="JOptionPane" />
            <ref role="37wK5l" to="dxuu:~JOptionPane.showMessageDialog(java.awt.Component,java.lang.Object):void" resolve="showMessageDialog" />
            <node concept="10Nm6u" id="4F6jbA$ln$j" role="37wK5m" />
            <node concept="37vLTw" id="4F6jbA$lnP1" role="37wK5m">
              <ref role="3cqZAo" node="4F6jbA$lnfY" resolve="message" />
            </node>
          </node>
        </node>
      </node>
      <node concept="3cqZAl" id="4F6jbA$lncV" role="3clF45" />
      <node concept="37vLTG" id="4F6jbA$lnfY" role="3clF46">
        <property role="TrG5h" value="message" />
        <node concept="17QB3L" id="4F6jbA$lnfX" role="1tU5fm" />
      </node>
    </node>
    <node concept="2XrIbr" id="nYv$LOtLrx" role="32lrUH">
      <property role="TrG5h" value="getFileName" />
      <node concept="3clFbS" id="nYv$LOtLry" role="3clF47">
        <node concept="1X3_iC" id="nYv$LOtOoK" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="3cpWs8" id="nYv$LOtb7T" role="8Wnug">
            <node concept="3cpWsn" id="nYv$LOtb7U" role="3cpWs9">
              <property role="TrG5h" value="fd" />
              <node concept="3uibUv" id="nYv$LOtb7V" role="1tU5fm">
                <ref role="3uigEE" to="z60i:~FileDialog" resolve="FileDialog" />
              </node>
              <node concept="2ShNRf" id="nYv$LOtzSh" role="33vP2m">
                <node concept="1pGfFk" id="nYv$LOt$rb" role="2ShVmc">
                  <ref role="37wK5l" to="z60i:~FileDialog.&lt;init&gt;(java.awt.Frame,java.lang.String,int)" resolve="FileDialog" />
                  <node concept="2OqwBi" id="nYv$LOt$tl" role="37wK5m">
                    <node concept="2WthIp" id="nYv$LOt$rA" role="2Oq$k0" />
                    <node concept="1DTwFV" id="nYv$LOt$AU" role="2OqNvi">
                      <ref role="2WH_rO" node="nYv$LOtyPM" resolve="frame" />
                    </node>
                  </node>
                  <node concept="Xl_RD" id="nYv$LOt$Da" role="37wK5m">
                    <property role="Xl_RC" value="Choose a grammar file" />
                  </node>
                  <node concept="10M0yZ" id="nYv$LOt$Ju" role="37wK5m">
                    <ref role="3cqZAo" to="z60i:~FileDialog.LOAD" resolve="LOAD" />
                    <ref role="1PxDUh" to="z60i:~FileDialog" resolve="FileDialog" />
                  </node>
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="1X3_iC" id="nYv$LOtOmL" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="3clFbF" id="nYv$LOtq1Z" role="8Wnug">
            <node concept="2OqwBi" id="nYv$LOtqbC" role="3clFbG">
              <node concept="37vLTw" id="nYv$LOtq1X" role="2Oq$k0">
                <ref role="3cqZAo" node="nYv$LOtb7U" resolve="fd" />
              </node>
              <node concept="liA8E" id="nYv$LOtqxO" role="2OqNvi">
                <ref role="37wK5l" to="z60i:~FileDialog.setFile(java.lang.String):void" resolve="setFile" />
                <node concept="Xl_RD" id="nYv$LOtqyl" role="37wK5m">
                  <property role="Xl_RC" value="*.g4" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="1X3_iC" id="nYv$LOtOi7" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="3clFbF" id="nYv$LOtqJ5" role="8Wnug">
            <node concept="2OqwBi" id="nYv$LOtqT8" role="3clFbG">
              <node concept="37vLTw" id="nYv$LOtqJ3" role="2Oq$k0">
                <ref role="3cqZAo" node="nYv$LOtb7U" resolve="fd" />
              </node>
              <node concept="liA8E" id="nYv$LOtrfA" role="2OqNvi">
                <ref role="37wK5l" to="z60i:~Dialog.setVisible(boolean):void" resolve="setVisible" />
                <node concept="3clFbT" id="nYv$LOtrg7" role="37wK5m">
                  <property role="3clFbU" value="true" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="nYv$LOtLZy" role="3cqZAp" />
        <node concept="1X3_iC" id="nYv$LOtOkr" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="3cpWs6" id="nYv$LOtM5R" role="8Wnug">
            <node concept="2OqwBi" id="nYv$LOtMcc" role="3cqZAk">
              <node concept="37vLTw" id="nYv$LOtM7O" role="2Oq$k0">
                <ref role="3cqZAo" node="nYv$LOtb7U" resolve="fd" />
              </node>
              <node concept="liA8E" id="nYv$LOtMzn" role="2OqNvi">
                <ref role="37wK5l" to="z60i:~FileDialog.getFile():java.lang.String" resolve="getFile" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs6" id="nYv$LOtMGs" role="3cqZAp">
          <node concept="Xl_RD" id="5Jh2F9ezZdF" role="3cqZAk">
            <property role="Xl_RC" value="/home/premun/Diplomka/ingrid/grammars/XML.g4" />
          </node>
        </node>
      </node>
      <node concept="17QB3L" id="nYv$LOtLPO" role="3clF45" />
    </node>
    <node concept="tnohg" id="3_5Fm_0LVaw" role="tncku">
      <node concept="3clFbS" id="3_5Fm_0LVax" role="2VODD2">
        <node concept="3cpWs8" id="mX_CB8FZwK" role="3cqZAp">
          <node concept="3cpWsn" id="mX_CB8FZwN" role="3cpWs9">
            <property role="TrG5h" value="languageName" />
            <node concept="17QB3L" id="mX_CB8FZwJ" role="1tU5fm" />
            <node concept="Xl_RD" id="mX_CB8FVGG" role="33vP2m">
              <property role="Xl_RC" value="imported.SimpleXML" />
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="mX_CB8FXZn" role="3cqZAp">
          <node concept="3cpWsn" id="mX_CB8FXZo" role="3cpWs9">
            <property role="TrG5h" value="language" />
            <node concept="3uibUv" id="mX_CB8FXZp" role="1tU5fm">
              <ref role="3uigEE" to="w1kc:~Language" resolve="Language" />
            </node>
            <node concept="10Nm6u" id="mX_CB8FXZq" role="33vP2m" />
          </node>
        </node>
        <node concept="3clFbH" id="mX_CB8FXZr" role="3cqZAp" />
        <node concept="3cpWs8" id="mX_CB8FXZs" role="3cqZAp">
          <node concept="3cpWsn" id="mX_CB8FXZt" role="3cpWs9">
            <property role="TrG5h" value="modules" />
            <node concept="A3Dl8" id="mX_CB8FXZu" role="1tU5fm">
              <node concept="3qUE_q" id="mX_CB8FXZv" role="A3Ik2">
                <node concept="3uibUv" id="mX_CB8FXZw" role="3qUE_r">
                  <ref role="3uigEE" to="lui2:~SModule" resolve="SModule" />
                </node>
              </node>
            </node>
            <node concept="2OqwBi" id="mX_CB8FXZx" role="33vP2m">
              <node concept="2OqwBi" id="mX_CB8FXZy" role="2Oq$k0">
                <node concept="2WthIp" id="mX_CB8FXZz" role="2Oq$k0" />
                <node concept="1DTwFV" id="mX_CB8FXZ$" role="2OqNvi">
                  <ref role="2WH_rO" node="3_5Fm_0M6RU" resolve="mpsProject" />
                </node>
              </node>
              <node concept="liA8E" id="mX_CB8FXZ_" role="2OqNvi">
                <ref role="37wK5l" to="z1c3:~Project.getModules():java.lang.Iterable" resolve="getModules" />
              </node>
            </node>
          </node>
        </node>
        <node concept="2Gpval" id="mX_CB8FXZA" role="3cqZAp">
          <node concept="2GrKxI" id="mX_CB8FXZB" role="2Gsz3X">
            <property role="TrG5h" value="modul" />
          </node>
          <node concept="3clFbS" id="mX_CB8FXZC" role="2LFqv$">
            <node concept="3clFbJ" id="mX_CB8FXZD" role="3cqZAp">
              <node concept="3clFbS" id="mX_CB8FXZE" role="3clFbx">
                <node concept="3clFbF" id="mX_CB8FXZF" role="3cqZAp">
                  <node concept="37vLTI" id="mX_CB8FXZG" role="3clFbG">
                    <node concept="2GrUjf" id="mX_CB8FXZH" role="37vLTx">
                      <ref role="2Gs0qQ" node="mX_CB8FXZB" resolve="modul" />
                    </node>
                    <node concept="37vLTw" id="mX_CB8FXZI" role="37vLTJ">
                      <ref role="3cqZAo" node="mX_CB8FXZo" resolve="language" />
                    </node>
                  </node>
                </node>
                <node concept="3zACq4" id="mX_CB8FXZJ" role="3cqZAp" />
              </node>
              <node concept="2OqwBi" id="mX_CB8FXZK" role="3clFbw">
                <node concept="2OqwBi" id="mX_CB8FXZL" role="2Oq$k0">
                  <node concept="2GrUjf" id="mX_CB8FXZM" role="2Oq$k0">
                    <ref role="2Gs0qQ" node="mX_CB8FXZB" resolve="modul" />
                  </node>
                  <node concept="liA8E" id="mX_CB8FXZN" role="2OqNvi">
                    <ref role="37wK5l" to="z1c3:~AbstractModule.getModuleName():java.lang.String" resolve="getModuleName" />
                  </node>
                </node>
                <node concept="liA8E" id="mX_CB8FXZO" role="2OqNvi">
                  <ref role="37wK5l" to="wyt6:~String.equals(java.lang.Object):boolean" resolve="equals" />
                  <node concept="37vLTw" id="mX_CB8G00R" role="37wK5m">
                    <ref role="3cqZAo" node="mX_CB8FZwN" resolve="languageName" />
                  </node>
                </node>
              </node>
            </node>
          </node>
          <node concept="2OqwBi" id="mX_CB8FXZQ" role="2GsD0m">
            <node concept="37vLTw" id="mX_CB8FXZR" role="2Oq$k0">
              <ref role="3cqZAo" node="mX_CB8FXZt" resolve="modules" />
            </node>
            <node concept="UnYns" id="mX_CB8FXZS" role="2OqNvi">
              <node concept="3uibUv" id="mX_CB8FXZT" role="UnYnz">
                <ref role="3uigEE" to="w1kc:~Language" resolve="Language" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5Jh2F9ezXzW" role="3cqZAp" />
        <node concept="3cpWs8" id="4X6IwqdNpSM" role="3cqZAp">
          <node concept="3cpWsn" id="4X6IwqdNpSN" role="3cpWs9">
            <property role="TrG5h" value="structureModel" />
            <node concept="3uibUv" id="4X6IwqdNpSO" role="1tU5fm">
              <ref role="3uigEE" to="mhbf:~SModel" resolve="SModel" />
            </node>
            <node concept="2OqwBi" id="4X6IwqdNpSP" role="33vP2m">
              <node concept="Rm8GO" id="4X6IwqdNpSQ" role="2Oq$k0">
                <ref role="1Px2BO" to="w1kc:~LanguageAspect" resolve="LanguageAspect" />
                <ref role="Rm8GQ" to="w1kc:~LanguageAspect.STRUCTURE" resolve="STRUCTURE" />
              </node>
              <node concept="liA8E" id="4X6IwqdNpSR" role="2OqNvi">
                <ref role="37wK5l" to="w1kc:~LanguageAspect.get(jetbrains.mps.smodel.Language):org.jetbrains.mps.openapi.model.SModel" resolve="get" />
                <node concept="37vLTw" id="4X6IwqdNpSS" role="37wK5m">
                  <ref role="3cqZAo" node="mX_CB8FXZo" resolve="language" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="5Jh2F9ezXWk" role="3cqZAp">
          <node concept="3cpWsn" id="5Jh2F9ezXWl" role="3cpWs9">
            <property role="TrG5h" value="editorModel" />
            <node concept="3uibUv" id="5Jh2F9ezY_F" role="1tU5fm">
              <ref role="3uigEE" to="mhbf:~SModel" resolve="SModel" />
            </node>
            <node concept="2OqwBi" id="5Jh2F9ezY88" role="33vP2m">
              <node concept="Rm8GO" id="4X6IwqdNqdR" role="2Oq$k0">
                <ref role="Rm8GQ" to="w1kc:~LanguageAspect.EDITOR" resolve="EDITOR" />
                <ref role="1Px2BO" to="w1kc:~LanguageAspect" resolve="LanguageAspect" />
              </node>
              <node concept="liA8E" id="5Jh2F9ezYib" role="2OqNvi">
                <ref role="37wK5l" to="w1kc:~LanguageAspect.get(jetbrains.mps.smodel.Language):org.jetbrains.mps.openapi.model.SModel" resolve="get" />
                <node concept="37vLTw" id="5Jh2F9ezYjz" role="37wK5m">
                  <ref role="3cqZAo" node="mX_CB8FXZo" resolve="language" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5Jh2F9ezZ4l" role="3cqZAp" />
        <node concept="3cpWs8" id="2CENt80j8HM" role="3cqZAp">
          <node concept="3cpWsn" id="2CENt80j8HN" role="3cpWs9">
            <property role="TrG5h" value="grammarImporter" />
            <node concept="3uibUv" id="2CENt80j8HO" role="1tU5fm">
              <ref role="3uigEE" to="zkjw:~GrammarImporter" resolve="GrammarImporter" />
            </node>
            <node concept="2ShNRf" id="2CENt80j8Iu" role="33vP2m">
              <node concept="1pGfFk" id="2CENt80jbpK" role="2ShVmc">
                <ref role="37wK5l" to="zkjw:~GrammarImporter.&lt;init&gt;(org.jetbrains.mps.openapi.model.SModel,org.jetbrains.mps.openapi.model.SModel)" resolve="GrammarImporter" />
                <node concept="37vLTw" id="4X6IwqdNqfV" role="37wK5m">
                  <ref role="3cqZAo" node="4X6IwqdNpSN" resolve="structureModel" />
                </node>
                <node concept="37vLTw" id="4X6IwqdNsR6" role="37wK5m">
                  <ref role="3cqZAo" node="5Jh2F9ezXWl" resolve="editorModel" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="nYv$LOtQnT" role="3cqZAp" />
        <node concept="3cpWs8" id="nYv$LOtNJz" role="3cqZAp">
          <node concept="3cpWsn" id="nYv$LOtNJA" role="3cpWs9">
            <property role="TrG5h" value="filename" />
            <node concept="17QB3L" id="nYv$LOtNJx" role="1tU5fm" />
            <node concept="2OqwBi" id="nYv$LOtNQJ" role="33vP2m">
              <node concept="2WthIp" id="nYv$LOtNQM" role="2Oq$k0" />
              <node concept="2XshWL" id="nYv$LOtNQO" role="2OqNvi">
                <ref role="2WH_rO" node="nYv$LOtLrx" resolve="getFileName" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbJ" id="nYv$LOtPzT" role="3cqZAp">
          <node concept="3clFbS" id="nYv$LOtPzV" role="3clFbx">
            <node concept="3cpWs6" id="nYv$LOtPNB" role="3cqZAp" />
          </node>
          <node concept="3clFbC" id="nYv$LOtPJj" role="3clFbw">
            <node concept="10Nm6u" id="nYv$LOtPK8" role="3uHU7w" />
            <node concept="37vLTw" id="nYv$LOtPFf" role="3uHU7B">
              <ref role="3cqZAo" node="nYv$LOtNJA" resolve="filename" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="nYv$LOtQ9B" role="3cqZAp" />
        <node concept="3clFbF" id="2CENt80jbvp" role="3cqZAp">
          <node concept="2OqwBi" id="2CENt80jbwA" role="3clFbG">
            <node concept="37vLTw" id="2CENt80jbvn" role="2Oq$k0">
              <ref role="3cqZAo" node="2CENt80j8HN" resolve="grammarImporter" />
            </node>
            <node concept="liA8E" id="2CENt80jbxG" role="2OqNvi">
              <ref role="37wK5l" to="zkjw:~GrammarImporter.importGrammar(java.lang.String):void" resolve="importGrammar" />
              <node concept="37vLTw" id="nYv$LOtNSE" role="37wK5m">
                <ref role="3cqZAo" node="nYv$LOtNJA" resolve="filename" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="nYv$LOtOPy" role="3cqZAp" />
        <node concept="3clFbF" id="nYv$LOtP9v" role="3cqZAp">
          <node concept="2OqwBi" id="nYv$LOtP9p" role="3clFbG">
            <node concept="2WthIp" id="nYv$LOtP9s" role="2Oq$k0" />
            <node concept="2XshWL" id="nYv$LOtP9u" role="2OqNvi">
              <ref role="2WH_rO" node="4F6jbA$ln8h" resolve="showAlert" />
              <node concept="Xl_RD" id="nYv$LOtPgO" role="2XxRq1">
                <property role="Xl_RC" value="Language imported" />
              </node>
            </node>
          </node>
        </node>
      </node>
    </node>
    <node concept="1DS2jV" id="3_5Fm_0M6RU" role="1NuT2Z">
      <property role="TrG5h" value="mpsProject" />
      <ref role="1DUlNI" to="qq03:~MPSCommonDataKeys.MPS_PROJECT" resolve="MPS_PROJECT" />
      <node concept="1oajcY" id="3_5Fm_0M6RV" role="1oa70y" />
    </node>
    <node concept="2S4$dB" id="4F6jbA$l4Ok" role="1NuT2Z">
      <property role="TrG5h" value="node" />
      <node concept="3Tm6S6" id="4F6jbA$l4Ol" role="1B3o_S" />
      <node concept="1oajcY" id="4F6jbA$l4Om" role="1oa70y" />
      <node concept="3Tqbb2" id="4F6jbA$l11s" role="1tU5fm" />
    </node>
    <node concept="1DS2jV" id="nYv$LOtyPM" role="1NuT2Z">
      <property role="TrG5h" value="frame" />
      <ref role="1DUlNI" to="qq03:~MPSCommonDataKeys.FRAME" resolve="FRAME" />
      <node concept="1oajcY" id="nYv$LOtyPN" role="1oa70y" />
    </node>
  </node>
  <node concept="tC5Ba" id="7u4wmVSPzXl">
    <property role="TrG5h" value="ImportActionGroup" />
    <node concept="ftmFs" id="7u4wmVSPzXn" role="ftER_">
      <node concept="tCFHf" id="7u4wmVSPzXr" role="ftvYc">
        <ref role="tCJdB" node="3_5Fm_0LVav" resolve="ImportAction" />
      </node>
    </node>
    <node concept="tT9cl" id="7u4wmVSP$1D" role="2f5YQi">
      <ref role="tU$_T" to="tprs:hyf4LYI" resolve="Tools" />
      <ref role="2f8Tey" to="tprs:hHYwqIT" resolve="customTools" />
    </node>
  </node>
</model>

