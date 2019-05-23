/*******************************************************************************
* Copyright (c) 2005 Prashant Deva and Gerd Castan
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License - v 1.0
* which is available at http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

package larvaplugin.editors;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;
import org.eclipse.swt.widgets.Display;

public class LarvaReconcilingStrategy implements IReconcilingStrategy,
               IReconcilingStrategyExtension {

       private LarvaEditor editor;

       private IDocument fDocument;

       /** holds the calculated positions */
       protected final ArrayList fPositions = new ArrayList();

       /** The offset of the next character to be read */
       protected int fOffset;

       /** The end offset of the range to be scanned */
       protected int fRangeEnd;

       /**
        * @return Returns the editor.
        */
       public LarvaEditor getEditor() {
               return editor;
       }

       public void setEditor(LarvaEditor editor) {
               this.editor = editor;
       }

       /*
        * (non-Javadoc)
        *
        * @see org.eclipse.jface.text.reconciler.IReconcilingStrategy#setDocument(org.eclipse.jface.text.IDocument)
        */
       public void setDocument(IDocument document) {
               this.fDocument = document;

       }

       /*
        * (non-Javadoc)
        *
        * @see org.eclipse.jface.text.reconciler.IReconcilingStrategy#reconcile(org.eclipse.jface.text.reconciler.DirtyRegion,
        *      org.eclipse.jface.text.IRegion)
        */
       public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
               initialReconcile();
       }

       /*
        * (non-Javadoc)
        *
        * @see org.eclipse.jface.text.reconciler.IReconcilingStrategy#reconcile(org.eclipse.jface.text.IRegion)
        */
       public void reconcile(IRegion partition) {
               initialReconcile();
       }

       /*
        * (non-Javadoc)
        *
        * @see org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension#setProgressMonitor(org.eclipse.core.runtime.IProgressMonitor)
        */
       public void setProgressMonitor(IProgressMonitor monitor) {
               // TODO Auto-generated method stub

       }

       /*
        * (non-Javadoc)
        *
        * @see org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension#initialReconcile()
        */
       public void initialReconcile() {
             calculatePositions();
       }
   
       protected int cNextPos = 0;

       protected void calculatePositions() {
               fPositions.clear();
               cNextPos = 0;
               fRangeEnd = fDocument.getLength();

               try {
            	   //emitPosition(50, 200);
                   recursiveTokens('{','}');
                   cNextPos = 0;
                   recursiveTokens('[',']');
              } catch (BadLocationException e) {
                       e.printStackTrace();
               }
               // Collections.sort(fPositions, new RangeTokenComparator());

               Display.getDefault().asyncExec(new Runnable() {
                       public void run() {
                               editor.updateFoldingStructure(fPositions);
                       }

               });
       }
  
       protected int recursiveTokens(char open, char close) throws BadLocationException {
    	   boolean lineSkipped = false;
    	   while (cNextPos < fRangeEnd) {
    		   char ch = fDocument.getChar(cNextPos++);

    		   int startOffset = cNextPos - 1;

    		   if (ch == open)
    		   {
    			   int length = recursiveTokens(open,close)-startOffset+2;
    			   if (length > 0)
    				   emitPosition(startOffset+1, length);
    		   }
    		   else if (ch == close)
    			   return (startOffset) * ((lineSkipped)?1:-1);      
    		   else if (ch == '\n' || ch == '\r') lineSkipped = true;
 
    	   }
    	    return (cNextPos-1) * ((lineSkipped)?1:-1);    
       }
       

       protected void emitPosition(int startOffset, int length) {
               fPositions.add(new Position(startOffset, length));
       }


 
}

