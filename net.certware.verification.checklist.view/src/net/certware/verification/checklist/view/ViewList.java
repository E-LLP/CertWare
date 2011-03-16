package net.certware.verification.checklist.view;

import net.certware.core.ICertWareConstants;
import net.certware.core.ui.handlers.LinkEditor;
import net.certware.core.ui.help.IHelpContext;
import net.certware.core.ui.listeners.ActiveEditorListener;
import net.certware.core.ui.log.CertWareLog;
import net.certware.core.ui.resources.FileFinder;
import net.certware.core.ui.resources.FileOpener;
import net.certware.core.ui.views.ICertWareView;
import net.certware.verification.checklist.Checklist;
import net.certware.verification.checklist.Choices;
import net.certware.verification.checklist.view.filters.ResultFilter;
import net.certware.verification.checklist.view.help.ContextProvider;
import net.certware.verification.checklist.view.preferences.PreferenceConstants;
import net.certware.verification.checklist.view.table.ChecklistModel;
import net.certware.verification.checklist.view.table.ChecklistTableViewer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.help.IContextProvider;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.HyperlinkSettings;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.part.ViewPart;

/**
 * Provides a list view of a checklist model, including buttons for updating the content.
 * @author mrb
 * @since 1.2.1
 */
public class ViewList extends ViewPart implements ICertWareConstants, ICertWareView, ISelectionListener, ISaveablePart2, IResourceChangeListener, IAdaptable, IHelpContext {

	/** the forms toolkit, borrowed from plugin's shared instance */
	private FormToolkit toolkit;
	/** the top-level scrolled form installed as the view control */
	private ScrolledForm form;
	/** memento for view part */
	protected IMemento memento = null;
	/** whether the view and editor are linked */
	public boolean isLinkingEditor = false;
	/** selected checklist model */
	private Checklist selectedChecklist = null;
	/** selected file */
	private IFile selectedFile = null;
	/** latest editor selection */
	private ISelection latestSelection = null;
	/** our listener for editor changes */
	private IPartListener2 activeEditorListener = null;
	/** context provider for help system adapter */
	static ContextProvider contextprovider = null;
	private ChecklistTableViewer tableViewer = null;
	private Section context;
	private Section items;
	private Hyperlink checklistText;
	private MenuItem itemYesFilterMenuItem;
	private MenuItem itemNoFilterMenuItem;
	private MenuItem itemUnknownFilterMenuItem;
	private MenuItem itemNaFilterMenuItem;
	private static final String CHECKLIST_LABEL = "Checklist: ";
	private static final String CHECKLIST_TOOL_TIP = "Checklist model name";
	private static final String MEMENTO_COLUMN_CATEGORY = "memento.category"; //$NON-NLS-1$
	private static final String MEMENTO_COLUMN_ID = "memento.id"; //$NON-NLS-1$
	private static final String MEMENTO_COLUMN_DESCRIPTION = "memento.description"; //$NON-NLS-1$
	private static final String MEMENTO_COLUMN_REFERENCE = "memento.reference"; //$NON-NLS-1$
	private static final String MEMENTO_COLUMN_COMMENT = "memento.comment"; //$NON-NLS-1$
	private static final String MEMENTO_COLUMN_CHOICES = "memento.choices"; //$NON-NLS-1$
	private static final String MEMENTO_SECTION_CONTEXT = "memento.context"; //$NON-NLS-1$
	private static final String MEMENTO_SECTION_ITEMS = "memento.items"; //$NON-NLS-1$
	private static final String MEMENTO_FILE = "memento.file"; //$NON-NLS-1$
	private static final String MEMENTO_FILTER_YES = "memento.filter.yes"; //$NON-NLS-1$
	private static final String MEMENTO_FILTER_NO = "memento.filter.no"; //$NON-NLS-1$
	private static final String MEMENTO_FILTER_UNKNOWN = "memento.filter.unknown"; //$NON-NLS-1$
	private static final String MEMENTO_FILTER_NA = "memento.filter.na"; //$NON-NLS-1$

	/**
	 * Initializes the part.
	 * Captures the selected file memento.
	 * @param site IViewSite
	 * @param memento IMemento
	 * @throws PartInitException 
	 * @see org.eclipse.ui.IViewPart#init(IViewSite, IMemento)
	 */
	@Override
	public void init(final IViewSite site, IMemento memento)
	throws PartInitException
	{
		super.init(site, memento);
		this.memento = memento; // can be null
	}

	/**
	 * Saves the view state.  
	 * The selected file and messages file are saved as a memento by file name.
	 * @param memento memento to save
	 * @see org.eclipse.ui.IViewPart#saveState(IMemento) */
	@Override
	public void saveState(IMemento memento)
	{
		super.saveState(memento);
		memento.putString(MEMENTO_FILE, selectedFile == null ? null : selectedFile.getName());
		memento.putBoolean(MEMENTO_SECTION_CONTEXT, context.isExpanded() );
		memento.putBoolean(MEMENTO_SECTION_ITEMS, items.isExpanded() );

		memento.putBoolean(MEMENTO_FILTER_YES, itemYesFilterMenuItem.getSelection());
		memento.putBoolean(MEMENTO_FILTER_NO, itemNoFilterMenuItem.getSelection());
		memento.putBoolean(MEMENTO_FILTER_UNKNOWN, itemUnknownFilterMenuItem.getSelection());
		memento.putBoolean(MEMENTO_FILTER_NA, itemNaFilterMenuItem.getSelection());

		TableColumn[] tcs = tableViewer.getTable().getColumns();
		memento.putInteger( MEMENTO_COLUMN_CATEGORY, tcs[0].getWidth());
		memento.putInteger( MEMENTO_COLUMN_ID, tcs[1].getWidth());
		memento.putInteger( MEMENTO_COLUMN_DESCRIPTION, tcs[2].getWidth());
		memento.putInteger( MEMENTO_COLUMN_REFERENCE, tcs[3].getWidth());
		memento.putInteger( MEMENTO_COLUMN_COMMENT, tcs[4].getWidth());
		memento.putInteger( MEMENTO_COLUMN_CHOICES, tcs[5].getWidth());
	}

	/**
	 * Set the context IDs for help system.  
	 * Wait to call until site has been established.
	 * @param control Control
	 */
	private void setHelpContextIDs(Control control) {
		IWorkbenchHelpSystem helpSystem = getSite().getWorkbenchWindow().getWorkbench().getHelpSystem();
		helpSystem.setHelp(control, IHelpContext.VCL_VIEW);
		// additional, as above...
	}

	/**
	 * Convenience method to add a column to the results table.
	 * @param columnNumber column number starting from zero
	 * @param table table destination
	 * @param menu menu parent for column header menus
	 * @param width initial column width
	 * @param style SWT style bits for the column
	 * @param id column ID for the model, from the viewers
	 * @param image a label image or null
	 * @return the new column 
	 */
	private TableColumn addColumn(int columnNumber, final Table table, Menu menu, int width, int style, final String id, Image image) {
		TableColumn column = new TableColumn(table, style, columnNumber );     
		column.setText(tableViewer.columnNames[columnNumber]);
		column.setWidth(width);
		column.setResizable(true);
		column.setMoveable(true);
		column.setImage(image);
		createMenuItem(menu,column);
		column.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tableViewer.getSorter().setColumn(id);
				tableViewer.refresh(true,true);
				TableColumn c = (TableColumn)e.getSource();
				setColumnImages(tableViewer,table,c);
				tableViewer.getControl().redraw();
				form.layout(true);
			}
		});

		return column;
	}

	/**
	 * Set the column header images according to sort direction.
	 * @param tv table viewer for sorter access
	 * @param t table for column access
	 * @param sc selected column to assign image
	 */
	private void setColumnImages(ChecklistTableViewer tv, Table t, TableColumn sc) {
		// clear all current images
		for ( int c = 0; c < t.getColumnCount(); c++ ) {
			TableColumn tc = t.getColumn(c);
			tc.setImage(null);
		}

		// set the selected column's image according to sort direction
		if ( tv.getSorter().getDirection() == SWT.UP )
			sc.setImage(Activator.getDefault().getImageRegistry().get(Activator.ASCENDING_IMAGE));
		else
			sc.setImage(Activator.getDefault().getImageRegistry().get(Activator.DESCENDING_IMAGE));
	}

	/**
	 * Creates and handles a column header menu to change column visibility.
	 * Item is enabled if column is resizable.
	 * Item is initially selected if its width is greater than zero.
	 * @param parent header menu
	 * @param column column for menu choice
	 */
	private void createMenuItem(Menu parent, final TableColumn column) {
		final MenuItem itemName = new MenuItem(parent, SWT.CHECK);
		itemName.setText(column.getText());
		itemName.setEnabled( column.getResizable() );
		itemName.setSelection( column.getWidth() > 0 );
		itemName.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (itemName.getSelection()) {
					column.setWidth(100);
					column.setResizable(true);
				} else {
					column.setWidth(0);
					column.setResizable(false);
				}
			}
		});
	}


	/**
	 * Create the view content using the forms widgets.
	 */
	@Override
	public void createPartControl(Composite parent) {
		TableWrapData twd;

		toolkit = Activator.getDefault().getFormToolkit(parent.getDisplay());
		toolkit.getHyperlinkGroup().setHyperlinkUnderlineMode(HyperlinkSettings.UNDERLINE_HOVER);
		form = toolkit.createScrolledForm(parent);
		form.setText("Verification Checklist");
		form.setMessage(null,IMessageProvider.NONE);
		form.setToolTipText("Select a checklist model");
		toolkit.decorateFormHeading(form.getForm());
		form.setImage(Activator.getDefault().getImageRegistry().getDescriptor(Activator.CHECKLIST_IMAGE).createImage());

		// layout
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 2;
		form.getBody().setLayout(layout);

		// context section
		context = toolkit.createSection(form.getBody(),	Section.DESCRIPTION | Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		twd = new TableWrapData(TableWrapData.FILL);
		twd.colspan = 2;
		context.setLayoutData(twd);
		context.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				form.layout(true);
				form.reflow(true);
			}
		});
		context.setText("Checklist Context");
		context.setToolTipText("Checklist identification content");
		Composite contextClient = toolkit.createComposite(context);
		contextClient.setLayout(new GridLayout());
		checklistText = toolkit.createHyperlink(contextClient, CHECKLIST_LABEL, SWT.WRAP);
		checklistText.setEnabled(false);
		checklistText.setToolTipText(CHECKLIST_TOOL_TIP);
		checklistText.setFont(JFaceResources.getDialogFont());
		checklistText.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				String sourceFile = (String)e.getHref(); // null until file selected, already contains extension
				if ( sourceFile == null ) return;
				IFile myFile = FileFinder.findResourceByName(sourceFile);
				FileOpener.findResourceEditor(myFile);
			}
		});

		//startTimeLabel = toolkit.createLabel(contextClient, START_TIME_LABEL);
		//startTimeLabel.setToolTipText(START_TIME_TOOL_TIP);
		context.setClient(contextClient);

		// results table section
		items = toolkit.createSection(form.getBody(), 
				Section.DESCRIPTION | Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		twd = new TableWrapData(TableWrapData.FILL_GRAB);
		twd.colspan = 2;
		twd.maxHeight = 500; 
		items.setLayoutData(twd);
		items.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				form.layout(true);
				form.reflow(true);
			}
		});
		items.setText("Checklist Items");
		items.setToolTipText("Current values of checklist items");
		Composite resultsClient2 = toolkit.createComposite(items);
		resultsClient2.setLayout(new FillLayout());

		int tableStyle = SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.HIDE_SELECTION;
		final Table table = new Table(resultsClient2,tableStyle);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 3;
		final Menu headerMenu = new Menu(table);

		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.addSelectionListener(new SelectionAdapter(){
			// position the file editor at the line number selected in the table
			@SuppressWarnings("unused")
			@Override
			public void widgetSelected(SelectionEvent e) {

				if ( !(getLinkingEditor()) || selectedChecklist == null )
					return;

				// table item data contains the model object
				TableItem ti = (TableItem)e.item;
				if ( ti == null ) return;
				ChecklistModel cm = (ChecklistModel)ti.getData();

				// find the file name
				/*
				try {
					int lineNumber = Integer.parseInt(lvm.getLine());

					// opens the editor if necessary, reveals and selects line
					FileOpener.editAtLineNumber(header.getFilename(),
							lineNumber,
							Activator.getDefault().getLog(),
							Activator.PLUGIN_ID);
				} catch(NumberFormatException nfe) {
					CertWareLog.logWarning("Exception parsing line number");
				}
				 */
			}});

		// menu for column widths
		table.addListener(SWT.MenuDetect, new Listener() {
			public void handleEvent(Event event) {
				table.setMenu(headerMenu);
			}
		});

		// set the viewer to the table and initialize
		tableViewer = new ChecklistTableViewer(table);
		tableViewer.initialize(null);

		// id column
		int columnNumber = 0;
		Image ascending = Activator.getDefault().getImageRegistry().get(Activator.ASCENDING_IMAGE);
		if ( memento == null || memento.getAttributeKeys().length < 6) {
			addColumn(columnNumber++,table,headerMenu,100,SWT.LEFT,ChecklistTableViewer.COLUMN_CATEGORY,ascending);
			addColumn(columnNumber++,table,headerMenu, 50,SWT.LEFT,ChecklistTableViewer.COLUMN_ID,null);
			addColumn(columnNumber++,table,headerMenu,150,SWT.LEFT,ChecklistTableViewer.COLUMN_DESCRIPTION,null);
			addColumn(columnNumber++,table,headerMenu,  0,SWT.LEFT,ChecklistTableViewer.COLUMN_REFERENCE,null);
			addColumn(columnNumber++,table,headerMenu,  0,SWT.LEFT,ChecklistTableViewer.COLUMN_COMMENT,null);
			addColumn(columnNumber++,table,headerMenu, 50,SWT.LEFT,ChecklistTableViewer.COLUMN_CHOICES,null);
		} else {
			addColumn(columnNumber++,table,headerMenu,memento.getInteger(MEMENTO_COLUMN_CATEGORY),SWT.LEFT,ChecklistTableViewer.COLUMN_CATEGORY,ascending);
			addColumn(columnNumber++,table,headerMenu,memento.getInteger(MEMENTO_COLUMN_ID),SWT.LEFT,ChecklistTableViewer.COLUMN_ID,null);
			addColumn(columnNumber++,table,headerMenu,memento.getInteger(MEMENTO_COLUMN_DESCRIPTION),SWT.LEFT,ChecklistTableViewer.COLUMN_DESCRIPTION,null);
			addColumn(columnNumber++,table,headerMenu,memento.getInteger(MEMENTO_COLUMN_REFERENCE),SWT.LEFT,ChecklistTableViewer.COLUMN_REFERENCE,null);
			addColumn(columnNumber++,table,headerMenu,memento.getInteger(MEMENTO_COLUMN_COMMENT),SWT.LEFT,ChecklistTableViewer.COLUMN_COMMENT,null);
			addColumn(columnNumber++,table,headerMenu,memento.getInteger(MEMENTO_COLUMN_CHOICES),SWT.LEFT,ChecklistTableViewer.COLUMN_CHOICES,null);
		}

		// add filters to the header menu 
		createMenuSeparator(headerMenu);
		ViewerFilter yesFilter = new ResultFilter(Choices.YES_VALUE);
		ViewerFilter noFilter = new ResultFilter(Choices.NO_VALUE);
		ViewerFilter unknownFilter = new ResultFilter(Choices.UNKNOWN_VALUE);
		ViewerFilter naFilter = new ResultFilter(Choices.NOT_APPLICABLE_VALUE);
		itemYesFilterMenuItem = createMenuFilterItem(headerMenu,"Hide Yes",yesFilter,false);
		itemNoFilterMenuItem = createMenuFilterItem(headerMenu,"Hide No",noFilter,false);
		itemUnknownFilterMenuItem = createMenuFilterItem(headerMenu,"Hide Unknown",unknownFilter,false);
		itemNaFilterMenuItem = createMenuFilterItem(headerMenu,"Hide N/A",naFilter,false);

		items.setClient(resultsClient2);

		// create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(form, IHelpContext.VCL_VIEW); 

		// add the selection listener
		// add the resource listener
		// add editor listener
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(this);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, 
				IResourceChangeEvent.POST_CHANGE | IResourceChangeEvent.PRE_CLOSE | IResourceChangeEvent.PRE_DELETE );

		activeEditorListener = new ActiveEditorListener(this);
		getSite().getPage().addPartListener(activeEditorListener);

		// sync with toggle commands
		setLinkingEditor( LinkEditor.getState() );

		// update with memento if available
		if ( memento != null ) {

			// file name
			String fileName = memento.getString(MEMENTO_FILE);
			if ( fileName != null ) { // && getLinkingEditor() == false ) {
				selectedFile = FileFinder.findResourceByName(fileName);
				setSelectedFile(selectedFile);
				getViewSite().getActionBars().getToolBarManager().markDirty();
			}

			// sections expanded
			context.setExpanded( memento.getBoolean(MEMENTO_SECTION_CONTEXT) );
			items.setExpanded( memento.getBoolean(MEMENTO_SECTION_ITEMS) );

			// filter states
			// set the menu item toggle and add the filter
			if (  memento.getBoolean(MEMENTO_FILTER_YES) ) {
				itemYesFilterMenuItem.setSelection( true );
				tableViewer.addFilter(yesFilter);
			}
			if (  memento.getBoolean(MEMENTO_FILTER_NO) ) {
				itemNoFilterMenuItem.setSelection( true );
				tableViewer.addFilter(noFilter);
			}
			if (  memento.getBoolean(MEMENTO_FILTER_UNKNOWN) ) {
				itemUnknownFilterMenuItem.setSelection( true );
				tableViewer.addFilter(unknownFilter);
			}
			if (  memento.getBoolean(MEMENTO_FILTER_NA) ) {
				itemNaFilterMenuItem.setSelection( true );
				tableViewer.addFilter(naFilter);
			}

			// column widths
			int column = 0;
			tableViewer.getTable().getColumn(column++).setWidth( memento.getInteger( MEMENTO_COLUMN_CATEGORY ));
			tableViewer.getTable().getColumn(column++).setWidth( memento.getInteger( MEMENTO_COLUMN_ID));
			tableViewer.getTable().getColumn(column++).setWidth( memento.getInteger( MEMENTO_COLUMN_DESCRIPTION ));
			tableViewer.getTable().getColumn(column++).setWidth( memento.getInteger( MEMENTO_COLUMN_REFERENCE ));
			tableViewer.getTable().getColumn(column++).setWidth( memento.getInteger( MEMENTO_COLUMN_COMMENT ));
			tableViewer.getTable().getColumn(column++).setWidth( memento.getInteger( MEMENTO_COLUMN_CHOICES ));
		}

		setHelpContextIDs(parent);

		form.layout();
	}

	/**
	 * Creates a menu item separator for the column header.
	 * @param parent column header menu
	 */
	private void createMenuSeparator(Menu parent) {
		new MenuItem(parent, SWT.SEPARATOR );
	}

	/**
	 * Creates a menu item for adding or removing a filter from the column header menu.
	 * Item is enabled and its selection depends on argument.
	 * @param parent column header menu
	 * @param label filter name label
	 * @param filter viewer filter for addition or removal
	 * @param selection current selection value  
	 * @return filter menu item  
	 */
	private MenuItem createMenuFilterItem(Menu parent, String label, final ViewerFilter filter, boolean selection) {
		final MenuItem itemName = new MenuItem(parent, SWT.CHECK);
		itemName.setText(label);
		itemName.setEnabled( true );
		itemName.setSelection( selection );
		itemName.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (itemName.getSelection()) {
					tableViewer.addFilter(filter);
				} else {
					tableViewer.removeFilter(filter);
				}
			}
		});

		return itemName;
	}


	/**
	 * Return the selected file.
	 * @return selected file, or null
	 */
	public IFile getSelectedFile() {
		return selectedFile;
	}

	/**
	 * Sets the selected file.
	 * @param f selected file
	 * @return 
	 */
	public boolean setSelectedFile(IFile ifile) {
		if ( ifile == null )
			return false;

		// if the file is the right type, open it and update the view
		String extension = ifile.getFileExtension();
		if (extension != null && ICertWareConstants.FILE_EXTENSIONS.contains(extension)) {

			try {
				// load the XML file through the EMF resource set implementation
				ResourceSet resourceSet = new ResourceSetImpl();
				Resource resource = resourceSet.getResource( URI.createPlatformResourceURI(ifile.getFullPath().toString(), true), true);
				Checklist root = (Checklist)resource.getContents().get(0);
				if ( root != null ) {
					selectedFile = ifile;
					selectedChecklist = root;
					updateView();
				}
			} catch( Exception exception ) {
				CertWareLog.logError(String.format("%s %s","Checklist null loading",ifile), exception);
				selectedFile = null;
				return false;
			}

			return true;
		} // if

		return false;
	}

	/**
	 * Return the selected model.
	 * @return selected model, or null
	 */
	public Checklist getSelectedChecklist() {
		return selectedChecklist;
	}

	/**
	 * Set the form focus.
	 */
	@Override
	public void setFocus() {
		form.setFocus();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// not needed
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public boolean isSaveOnCloseNeeded() {
		return isDirty();
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		final IPreferenceStore ps = Activator.getDefault().getPreferenceStore();

		try {
			event.getDelta().accept(new IResourceDeltaVisitor() {

				public boolean visit(IResourceDelta delta) throws CoreException {
					if ( delta.getKind() == IResourceDelta.REMOVED ) {
						// selected results file
						if ( selectedFile != null )
							if ( delta.getResource() instanceof IFile && selectedFile.equals(delta.getResource()) ) {
								latestSelection = null;
								selectionChanged(null,latestSelection); // refresh the display with no selection
								clearView();
							}
					} // removed
					else
						if ( delta.getKind() == IResourceDelta.CHANGED ) {
							if ( selectedFile != null )
								if ( ps.getBoolean( PreferenceConstants.P_CHECKLIST_VIEW_REFRESH_ON_RESOURCE_CHANGE ) )
									if ( delta.getResource() instanceof IFile && selectedFile.equals(delta.getResource()) ) {
										selectionChanged(null,latestSelection); // refresh the display
									}
						} // changed
					return true;
				}
			});
		} catch( CoreException ce ) {
			CertWareLog.logWarning(String.format("%s: %s","Exception refreshing selected checklist file",ce.getMessage()));
		}

	}

	/**
	 * Updates the checklist table content.
	 */
	protected void updateChecklistTable() {
		if ( selectedChecklist == null )
			return;

		tableViewer.initialize(selectedChecklist);
		tableViewer.setInput(selectedChecklist);
		tableViewer.refresh();
		tableViewer.getControl().pack(true);
	}


	/**
	 * Clears the contents of the view.
	 * Used primarily when the associate resource becomes unavailable.
	 */
	protected void clearView() {

		if ( checklistText == null )
			return;

		context.getDisplay().asyncExec(new Runnable(){
			public void run() {
				checklistText.setText(CHECKLIST_LABEL + "<none>");
				checklistText.setEnabled(false);

				tableViewer.setItemCount(0);
				tableViewer.refresh();

				form.reflow(true);
			}
		});
	}

	/**
	 * Update the view.
	 */
	protected void updateView() {
		if ( selectedChecklist == null ) 
			return;

		checklistText.getDisplay().asyncExec(new Runnable() {

			public void run() {
				try {
					// update the context section
					String checklistName = selectedChecklist.getName();
					String programString = null;

					if ( checklistName != null ) {
						programString = checklistName + ' ' + selectedChecklist.getVersion();
					}

					// always redraw the strings to erase any previous content
					// the form composite requires re-packing to reflect the new boundaries
					checklistText.setText(CHECKLIST_LABEL + programString);
					checklistText.setHref(programString);
					checklistText.setEnabled(true);
					checklistText.pack(true);

					updateChecklistTable();

					// refresh the form layout
					// form.layout(true);
					form.reflow(true);

				} catch( SWTException e ) {
					// ignore disposed cases, pass along others
					if ( e.code != SWT.ERROR_DEVICE_DISPOSED )
						throw e;
				}
			}
		});
	}

	/**
	 * Prompt to save on close.
	 * @return always returns yes
	 */
	@Override
	public int promptToSaveOnClose() {
		if ( isDirty() )
			return ISaveablePart2.YES;
		else
			return ISaveablePart2.NO;
	}

	/**
	 * Process a selection change.
	 * @param part workbench part
	 * @param selection selection to process
	 */
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if ( ! (selection instanceof IStructuredSelection ))
			return;

		IStructuredSelection iss = (IStructuredSelection)selection;

		// selecting from the explorer without the file open
		if ( iss.getFirstElement() instanceof IFile ) { 
			if ( setSelectedFile((IFile)iss.getFirstElement()) )
				latestSelection = selection;
		} 

		// otherwise select from the active model editor
		if ( ! (iss.getFirstElement() instanceof EObject) ) 
			return;

		EObject eo = (EObject)iss.getFirstElement();

		// find the containing document object for results objects
		while( eo != null ) {
			if ( eo instanceof Checklist ) {
				selectedChecklist = (Checklist)eo;
				updateView();
				latestSelection = selection;
				return;
			}
			eo = eo.eContainer();
		}
	}

	/**
	 * Dispose of listeners and other resources.
	 * The plugin takes care of the forms toolkit.
	 * @see org.eclipse.ui.IWorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);
		getSite().getPage().removePartListener(activeEditorListener);
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	/**
	 * Returns whether the view is linking the editor.
	 * @return true if linking editor
	 */
	public boolean getLinkingEditor() {
		return isLinkingEditor;
	}

	/**
	 * Sets the linking editor state.
	 * @param le true if the view should listen to the active editor
	 */
	public void setLinkingEditor(boolean le) {
		isLinkingEditor = le;
	}

	/**
	 * Adapter to identify context provider.
	 * @param key context provider type desired
	 * @return context provider 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class key) {

		if (key.equals(IContextProvider.class)) {
			if (contextprovider == null)
				contextprovider = new ContextProvider(this);
			return contextprovider;
		}

		return super.getAdapter(key);
	}

	@Override
	public String getDefaultExtension() {
		return ICertWareConstants.VCL_EXTENSION;
	}


}