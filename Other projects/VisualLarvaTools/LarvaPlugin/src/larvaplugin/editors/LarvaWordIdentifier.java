package larvaplugin.editors;

import org.eclipse.jface.text.rules.IWordDetector;

public class LarvaWordIdentifier implements IWordDetector {
	
		public boolean isWordPart(char character) {
			return character == '>'
				|| Character.isJavaIdentifierPart(character);
			//Character.isLetter(character) || Character.isSupplementaryCodePoint(character);
		}
		public boolean isWordStart(char character) {
			return character == '-'
				|| Character.isJavaIdentifierPart(character);
			//Character.isLetter(character) || Character.isSupplementaryCodePoint(character);
		}
	}
//this class decides what is part of a word and not... example "global{" will not be recognised as "Global" 
//but using isJavaIdentifierPart it will because '{' is not part of an identifier