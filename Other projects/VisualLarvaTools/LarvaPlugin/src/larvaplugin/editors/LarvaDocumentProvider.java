package larvaplugin.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.projection.ProjectionDocument;
import org.eclipse.jface.text.projection.ProjectionDocumentManager;
import org.eclipse.jface.text.rules.DefaultPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

public class LarvaDocumentProvider extends FileDocumentProvider{

	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		if(document != null) {
			LarvaPartitionScanner scanner = new LarvaPartitionScanner();
			 IDocumentPartitioner partitioner = new DefaultPartitioner(scanner, LarvaPartitionScanner.PARTITION_TYPES);
			 document.setDocumentPartitioner(partitioner);
			 partitioner.connect(document);			
			 
			 ProjectionDocumentManager manager = new ProjectionDocumentManager();
			 ProjectionDocument projectionDocument =(ProjectionDocument)manager.createSlaveDocument(document);
		}
		return document;
	}
	

}
