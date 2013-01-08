package net.jeeeyul.pdetools.snapshot.editor;

import java.io.File;
import java.io.FileInputStream;

import net.jeeeyul.pdetools.model.pdetools.PdetoolsPackage;
import net.jeeeyul.pdetools.model.pdetools.SnapshotEntry;
import net.jeeeyul.pdetools.shared.ImageCanvas;
import net.jeeeyul.pdetools.snapshot.SnapshotEditorInput;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class SnapshotEditor extends EditorPart {
	public static final String ID = "net.jeeeyul.pdetools.snapshot.editor.SnapshotEditor";
	private ImageCanvas imageCanvas;

	private Thread imageLoading = new Thread() {
		public void run() {
			doLoad();
		};
	};

	private EContentAdapter inputAdapter = new EContentAdapter() {
		public void notifyChanged(Notification notification) {
			if (!notification.isTouch()) {
				handleInputChangeEvent(notification);
			}
			super.notifyChanged(notification);
		};
	};

	protected void handleInputChangeEvent(Notification notification) {
		switch (notification.getEventType()) {
			case Notification.SET:
			case Notification.UNSET:
				if (notification.getFeature() == PdetoolsPackage.eINSTANCE.getSnapshotEntry_Parent()) {
					if (getEditorInput().getSnapshot().getParent() == null) {
						doClose();
					}
				}
				break;
			default:
		}
	}

	private void doClose() {
		getSite().getPage().closeEditor(this, false);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {

	}

	protected void doLoad() {
		SnapshotEntry snapshot = getEditorInput().getSnapshot();

		File file = new File(snapshot.getAbsoulteVisibleFilePath());
		try {
			FileInputStream fis = new FileInputStream(file);
			final ImageData data = new ImageData(fis);
			fis.close();

			imageCanvas.getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					if (imageCanvas.isDisposed()) {
						return;
					}
					Image image = new Image(imageCanvas.getDisplay(), data);
					imageCanvas.setImage(image);
				}
			});
			snapshot.eAdapters().add(inputAdapter);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dispose() {
		if (imageCanvas.getImage() != null) {
			imageCanvas.getImage().dispose();
		}
		super.dispose();
		getEditorInput().getSnapshot().eAdapters().remove(inputAdapter);
	}

	@Override
	public void doSaveAs() {

	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);

		setPartName(input.getName());
	}

	@Override
	public SnapshotEditorInput getEditorInput() {
		return (SnapshotEditorInput) super.getEditorInput();
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		imageCanvas = new ImageCanvas(parent);
		imageCanvas.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		imageLoading.start();
	}

	@Override
	public void setFocus() {

	}

}
