package larvaplugin.editors;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.TextEditor;

public class LarvaEditor extends TextEditor{
	
	private ProjectionSupport projectionSupport;
	
	private Annotation[] oldAnnotations;
	private HashMap<Position, Annotation> existing = new HashMap<Position, Annotation>();
	//private HashSet<Position> deleted = new HashSet<Position>();

	private ProjectionAnnotationModel annotationModel;

	
	public LarvaEditor()
	{
		super();
		setSourceViewerConfiguration(new LarvaSourceViewerConfiguration(this));
		setDocumentProvider(new LarvaDocumentProvider());	 
	}
	
	public void dispose()
	{ super.dispose(); }
	
	public void createPartControl(Composite parent)
	{
		super.createPartControl(parent);
		ProjectionViewer viewer =(ProjectionViewer)getSourceViewer();

		projectionSupport = new ProjectionSupport(viewer,getAnnotationAccess(),getSharedColors());
		projectionSupport.install();

		//turn projection mode on
		viewer.doOperation(ProjectionViewer.TOGGLE);

		annotationModel = viewer.getProjectionAnnotationModel();

	}
	
	public Annotation[] ListToArray (Collection<Annotation> l)
	{
		Annotation[] arraylist = new Annotation[l.size()];
		int i = 0;
		for (Annotation a : l)
		{
			arraylist[i] = a;
			i ++;
		}
		return arraylist;
	}
	
	public void updateFoldingStructure(ArrayList positions)
	{
		//ArrayList<Annotation> annotations = new ArrayList<Annotation>();
		
		//this will hold the new annotations along
		//with their corresponding positions
		HashMap newAnnotations = new HashMap();
		HashMap<Position, Annotation> deleted = (HashMap<Position, Annotation>)existing.clone();
		
		for(int i =0;i<positions.size();i++)
		{
			if (!existing.containsKey((Position)positions.get(i)))
			{
				
				ProjectionAnnotation annotation = new ProjectionAnnotation();
				newAnnotations.put(annotation,positions.get(i));
				existing.put((Position)positions.get(i),annotation);
				//annotations.add(annotation);
			}
			else
			{
				deleted.remove((Position)positions.get(i));
			}
		}

				
		annotationModel.modifyAnnotations(ListToArray(deleted.values()), newAnnotations, null);

		for (Position p : deleted.keySet())
			existing.remove(p);
		//oldAnnotations = existing.toArray(new Annotation[annotations.size()]);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.texteditor.AbstractTextEditor#createSourceViewer(org.eclipse.swt.widgets.Composite, org.eclipse.jface.text.source.IVerticalRuler, int)
	 */
	protected ISourceViewer createSourceViewer(Composite parent,
			IVerticalRuler ruler, int styles)
	{
		ISourceViewer viewer = new ProjectionViewer(parent, ruler, getOverviewRuler(), isOverviewRulerVisible(), styles);

		// ensure decoration support has been created and configured.
		getSourceViewerDecorationSupport(viewer);

		return viewer;
	}

}
