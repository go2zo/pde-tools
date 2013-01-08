package net.jeeeyul.pdetools.biv

import net.jeeeyul.pdetools.shared.SWTExtensions
import net.jeeeyul.pdetools.shared.SharedImages
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Text
import org.eclipse.swt.widgets.ToolBar

class Filter {
	extension SWTExtensions = SWTExtensions::INSTANCE
	
	Text textField
	Composite container
	ToolBar toolbar
	
	new(Composite parent){
		container = parent.newComposite[
			layout = newGridLayout[
				numColumns = 2
				marginWidth = 0
				marginHeight = 2
			]
			
			newLabel[
				text = "Bundle Filter:"
			]
			
			newCompositeWithStyle(SWT::BORDER)[
				layoutData = FILL_HORIZONTAL
				layout = newGridLayout[
					numColumns = 2
					marginWidth = 0
					marginHeight = 0
				]
			
				textField = newTextField(SWT::NONE)[
					message = "type bundle filter text"
					layoutData = FILL_HORIZONTAL
					onModified = [
						updateToolbarVisibility()
					]
				]
				
				toolbar = newToolBar[
					newToolItem[
						image = SharedImages::getImage(SharedImages::ERASER)
						onSelection = [
							textField.text = ""
						]
					]
				]
			]
		]
		
		updateToolbarVisibility()
	}
	
	def getTextField(){
		textField
	}
	
	def getControl(){
		container
	}

	def void updateToolbarVisibility() { 
		toolbar.visible = !textField.text.trim.empty
	}
}