package net.jeeeyul.pdetools.snapshot.handlers

import net.jeeeyul.pdetools.model.pdetools.SnapshotRepository
import net.jeeeyul.pdetools.snapshot.SnapshotAction
import net.jeeeyul.pdetools.snapshot.SnapshotCore

class RedoAction extends SnapshotAction{
	
	new(SnapshotRepository repository) {
		super(repository)
	}
	
	override run() {
		editDomain.commandStack.redo()
	}
	
	override update() {
		setEnabled(editDomain.commandStack.canRedo)
		
		text = if(enabled){
			'''Redo «editDomain.commandStack.redoCommand.label»'''			
		}else{
			"Redo"
		}
	}
	
	def getEditDomain(){
		SnapshotCore::editingDomain
	}

}