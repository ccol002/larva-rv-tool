/*******************************************************************************
 * Copyright (c) 2005 Prashant Deva.
 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License - v 1.0
 * which is available at http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

package larvaplugin.editors;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class LarvaWhitespaceDetector implements IWhitespaceDetector {

	public boolean isWhitespace(char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r' || c=='{' || c=='[' || c=='(' || c=='}' || c==']' || c==')' );
	}

	public static boolean isSpace(char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r' || c=='{' || c=='[' || c=='(' || c=='}' || c==']' || c==')' );
	}


}
