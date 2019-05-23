package larvaplugin.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;


public class LarvaSourceViewerConfiguration extends SourceViewerConfiguration{

	private LarvaEditor editor;
	
	private LarvaRuleBasedScanner lrbs; 
	private LarvaPartitionScanner lrbps; 
	
	
	public LarvaSourceViewerConfiguration(LarvaEditor editor)
	{
		this.editor = editor;
	}
	
	public RuleBasedScanner getRuleBasedScanner()
	{
		if (lrbs == null)
			lrbs = new LarvaRuleBasedScanner();
		return lrbs;
	}
	
	public RuleBasedScanner getPartitionScanner()
	{
		if (lrbps == null)
			lrbps = new LarvaPartitionScanner();
		return lrbps;
	}
	
	public int getTabWidth(ISourceViewer sourceViewer) {		
		return 2;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getPresentationReconciler(org.eclipse.jface.text.source.ISourceViewer)
	 */
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		
		PresentationReconciler reconciler = new PresentationReconciler();
		
		DefaultDamagerRepairer dr;
		
	
		dr = new DefaultDamagerRepairer(getRuleBasedScanner());		
		reconciler.setDamager(dr,LarvaPartitionScanner.MULTI_LINE_ACTION);
		reconciler.setRepairer(dr,LarvaPartitionScanner.MULTI_LINE_ACTION);	
		
		dr = new DefaultDamagerRepairer(getRuleBasedScanner());		
		reconciler.setDamager(dr,LarvaPartitionScanner.SINLGE_LINE_COMMENT);
		reconciler.setRepairer(dr,LarvaPartitionScanner.SINLGE_LINE_COMMENT);
		
//		dr = new DefaultDamagerRepairer(getRuleBasedScanner());		
//		reconciler.setDamager(dr,LarvaPartitionScanner.WORD);
//		reconciler.setRepairer(dr,LarvaPartitionScanner.WORD);	
		
		
		//the rest of the rules
		dr = new DefaultDamagerRepairer(getRuleBasedScanner());		
		reconciler.setDamager(dr,IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr,IDocument.DEFAULT_CONTENT_TYPE);	
		
//		dr = new DefaultDamagerRepairer(getRuleBasedScanner());		
//		reconciler.setDamager(dr,IDocument.DEFAULT_CATEGORY);
//		reconciler.setRepairer(dr,IDocument.DEFAULT_CATEGORY);	
		
//		dr = new DefaultDamagerRepairer(getRuleBasedScanner());		
//		reconciler.setDamager(dr,LarvaPartitionScanner.WORDS);
//		reconciler.setRepairer(dr,LarvaPartitionScanner.WORDS);	
//		
//		dr = new DefaultDamagerRepairer(getRuleBasedScanner());		
//		reconciler.setDamager(dr,LarvaPartitionScanner.WORDS2);
//		reconciler.setRepairer(dr,LarvaPartitionScanner.WORDS2);
//		
		
		
		
//		NonRuleBasedDamagerRepairer ndr =
//			new NonRuleBasedDamagerRepairer(
//				new TextAttribute(
//					Display.getCurrent().getSystemColor(SWT.COLOR_RED)));
//		reconciler.setDamager(ndr,LarvaPartitionScanner.MULTI_LINE_COMMENT);
//		reconciler.setRepairer(ndr, LarvaPartitionScanner.MULTI_LINE_COMMENT);
		
		
//		NonRuleBasedDamagerRepairer ndr =
//			new NonRuleBasedDamagerRepairer(
//				new TextAttribute(
//					Display.getCurrent().getSystemColor(SWT.COLOR_DARK_MAGENTA)));
//		reconciler.setDamager(ndr,LarvaPartitionScanner.WORDS2);
//		reconciler.setRepairer(ndr, LarvaPartitionScanner.WORDS2);
		
		
//		dr = new DefaultDamagerRepairer(new LarvaRuleBasedScanner());		
//		reconciler.setDamager(dr,LarvaPartitionScanner.STATES);
//		reconciler.setRepairer(dr,LarvaPartitionScanner.STATES);	
//		
//		dr = new DefaultDamagerRepairer(new LarvaRuleBasedScanner());		
//		reconciler.setDamager(dr,LarvaPartitionScanner.FOREACH);
//		reconciler.setRepairer(dr,LarvaPartitionScanner.FOREACH);	
//		
//		dr = new DefaultDamagerRepairer(new LarvaRuleBasedScanner());		
//		reconciler.setDamager(dr,LarvaPartitionScanner.GLOBAL);
//		reconciler.setRepairer(dr,LarvaPartitionScanner.GLOBAL);	
//	
//		dr = new DefaultDamagerRepairer(new LarvaRuleBasedScanner());		
//		reconciler.setDamager(dr,LarvaPartitionScanner.PROPERTY);
//		reconciler.setRepairer(dr,LarvaPartitionScanner.PROPERTY);	
		
		return reconciler;
	}
	
    public IContentAssistant getContentAssistant(ISourceViewer sourceViewer)
    {
        ContentAssistant assistant = new ContentAssistant();
        assistant.setContentAssistProcessor(new LarvaContentAssistantProcessor(),IDocument.DEFAULT_CONTENT_TYPE);
        assistant.enableAutoActivation(true);
        
        return assistant;
    }

	public IReconciler getReconciler(ISourceViewer sourceViewer)
	    {
	        LarvaReconcilingStrategy strategy = new LarvaReconcilingStrategy();
	        strategy.setEditor(editor);
	        
	        MonoReconciler reconciler = new MonoReconciler(strategy,false);
	        
	        return reconciler;
	    }
	
}
