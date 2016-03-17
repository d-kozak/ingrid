package premun.mps.ingrid.importer;

import jetbrains.mps.internal.collections.runtime.*;
import jetbrains.mps.lang.smodel.generator.smodelAdapter.*;
import jetbrains.mps.smodel.adapter.structure.*;
import org.jetbrains.mps.openapi.language.*;
import org.jetbrains.mps.openapi.model.*;
import premun.mps.ingrid.parser.*;
import premun.mps.ingrid.parser.grammar.*;

public class GrammarImporter {
    private SModel structureModel;
    private GrammarInfo grammar;

    public GrammarImporter(SModel structureModel) {
        this.structureModel = structureModel;
    }

    private void initializeLanguage() {
        // Delete all nodes
        SModelOperations
            .nodes(this.structureModel, null)
            .stream()
            .forEach(SNodeOperations::deleteNode);
    }

    public void importGrammar(String fileName) {
        initializeLanguage();

        this.grammar = GrammarParser.parseFile(fileName);

        this.importTokens();
        this.importRules();
    }

    private void importRules() {
        this.grammar.rules
            .values()
            .stream()
            .filter(r -> r instanceof ParserRule)
            .forEach(r -> this.importRule((ParserRule) r));
    }

    private void importTokens() {
        this.grammar.rules
            .values()
            .stream()
            .filter(r -> r instanceof FlatLexerRule)
            .forEach(r -> this.importToken((FlatLexerRule) r));
    }

    private void importRule(ParserRule rule) {
        if (rule.alternatives.size() > 1) {
            // Rule with more alternatives - we will create an interface
            // and a child for each alternative that will inherit this interface
            SNode iface = NodeHelper.createNode(NodeType.Interface, rule.name, rule.name, "Interfaces", this.structureModel);
            this.structureModel.addRootNode(iface);

            // For each alternative, there will be a concept
            for (int i = 1; i <= rule.alternatives.size(); ++i) {
                // TODO: if only one element is contained inside, we can flatten this rule and delete this intermediate step by advancing to the next step
                String name = rule.name + "_" + i;
                SNode alternativeNode = NodeHelper.createNode(NodeType.Concept, name, name, "Rules", this.structureModel);
                this.structureModel.addRootNode(alternativeNode);
                this.linkInterfaceToConcept(iface, alternativeNode);
            }
        } else {
            // Concrete element, we can create a concept
            SNode node = NodeHelper.createNode(NodeType.Concept, rule.name, rule.name, "Rules", this.structureModel);

            if (rule.equals(this.grammar.rootRule)) {
                NodeHelper.setProperty(node, Properties.ROOTABLE, "true");
            }

            this.structureModel.addRootNode(node);
        }
    }

    private void importToken(FlatLexerRule rule) {
        if (rule instanceof RegexRule) {
            SNode node = NodeHelper.createNode(NodeType.ConstraintDataType, rule.name, rule.name, "Tokens", this.structureModel);
            NodeHelper.setProperty(node, Properties.CONSTRAINT, ((RegexRule) rule).regexp);
            this.structureModel.addRootNode(node);
        }
    }

    private void linkInterfaceToConcept(SNode iface, SNode concept) {
        // Create interface link
        SNode interfaceReference = NodeHelper.createNode(NodeType.InterfaceReference, null, null, null, null);
        SReferenceLink interfaceLink = MetaAdapterFactory.getReferenceLink(0xc72da2b97cce4447L, 0x8389f407dc1158b7L, 0x110356fc618L, 0x110356fe029L, "intfc");
        SLinkOperations.setTarget(interfaceReference, interfaceLink, iface);

        // Implements field of the concept
        SContainmentLink implementsLink = MetaAdapterFactory.getContainmentLink(0xc72da2b97cce4447L, 0x8389f407dc1158b7L, 0xf979ba0450L, 0x110358d693eL, "implements");
        // Add interface to implements field
        ListSequence.fromList(SLinkOperations.getChildren(concept, implementsLink)).addElement(interfaceReference);
    }

    private SNode translateRuleToConcept(Rule rule) {
        for (SNode node : this.structureModel.getRootNodes()) {
            if (rule.name.equals(node.getName())) {
                return node;
            }
        }

        return null;
    }
}