/*
 * NOT generated by Xtext
 */
package net.certware.intent.ui.quickfix

import net.certware.intent.intentSpecification.Decomposition
import net.certware.intent.intentSpecification.Intent
import net.certware.intent.intentSpecification.ListItem
import net.certware.intent.intentSpecification.ModelItem
import net.certware.intent.intentSpecification.Refinement
import net.certware.intent.validation.IntentSpecificationValidator
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider
import org.eclipse.xtext.ui.editor.quickfix.Fix
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor
import org.eclipse.xtext.validation.Issue

/**
 * Custom quickfixes.
 * @see http://www.eclipse.org/Xtext/documentation.html#quickfixes
 * @author mrb@certware.net
 */
class IntentSpecificationQuickfixProvider extends DefaultQuickfixProvider {
	
	/*
	 * Fix duplicated intent entries by removing one.
	 */
	@Fix(IntentSpecificationValidator::UNIQUE_INTENTS_REFINEMENT)
	def removeIntent(Issue issue, IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue,
			"Remove duplicated entry", // label
			"Remove intent named" + ' ' + issue.data.get(0), // description
			"obj16/intent.gif", // icon
			[
				element, context |
				var container = element as Refinement
				var itemName = issue.data.get(0) as String
				var list = container.intents
				var iter = list.iterator
				var lastItem = null as Intent
				while(iter.hasNext) {
					var item = iter.next()
					if ( item.name.equals(itemName)) {
						lastItem = item
					}
				}
				
				if (lastItem != null ) {
					list.remove(lastItem)
				}
			]
		);
	}

	/*
	 * Fix duplicated intent entries by removing one.
	 */
	@Fix(IntentSpecificationValidator::UNIQUE_DECOMPOSITION_INTENT)
	def removeDecomposition(Issue issue, IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue,
			"Remove duplicated decomposition", // label
			"Remove decomposition named" + ' ' + issue.data.get(0), // description
			"obj16/decomposition.gif", // icon
			[
				element, context |
				var container = element as Intent
				var itemName = issue.data.get(0) as String
				var list = container.decompositions
				var iter = list.iterator
				var lastItem = null as Decomposition
				while(iter.hasNext) {
					var item = iter.next() as Decomposition
					if ( item.name.equals(itemName)) {
						lastItem = item
					}
				}
				
				if (lastItem != null ) {
					list.remove(lastItem)
				}
			]
		);
	}
	/*
	 * Fix duplicated list entries by removing one.
	 */
	@Fix(IntentSpecificationValidator::UNIQUE_LIST_ITEM_LIST_ITEM)
	def removeListItem(Issue issue, IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue,
			"Remove duplicated list item", // label
			"Remove list item named" + ' ' + issue.data.get(0), // description
			"obj16/listitem.gif", // icon
			[
				element, context |
				var outerList = element as ListItem
				var itemName = issue.data.get(0) as String
				var list = outerList.itemReferences
				var iter = list.iterator
				var lastItem = null as ListItem
				while(iter.hasNext) {
					var item = iter.next()
					if ( item.name.equals(itemName)) {
						lastItem = item
					}
				}
				
				if (lastItem != null ) {
					list.remove(lastItem)
				}
			]
		);
	}
	
	@Fix(IntentSpecificationValidator::UNIQUE_MODEL_ITEM_LIST_ITEM)
	def removeModelItem(Issue issue, IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue,
			"Remove duplicated entry", // label
			"Remove model item named" + ' ' + issue.data.get(0), // description
			"obj16/modelitem.gif", // icon
			[
				element, context |
				var outerList = element as ListItem
				var itemName = issue.data.get(0) as String
				var list = outerList.modelReferences
				var iter = list.iterator
				var lastItem = null as ModelItem
				while(iter.hasNext) {
					var item = iter.next()
					if ( item.name.equals(itemName)) {
						lastItem = item
					}
				}
				
				if (lastItem != null ) {
					list.remove(lastItem)
				}
			]
		);
	}

//	@Fix(MyDslValidator::INVALID_NAME)
//	def capitalizeName(Issue issue, IssueResolutionAcceptor acceptor) {
//		acceptor.accept(issue, 'Capitalize name', 'Capitalize the name.', 'upcase.png') [
//			context |
//			val xtextDocument = context.xtextDocument
//			val firstLetter = xtextDocument.get(issue.offset, 1)
//			xtextDocument.replace(issue.offset, 1, firstLetter.toUpperCase)
//		]
//	}
}
