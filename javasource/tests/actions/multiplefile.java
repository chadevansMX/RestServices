// This file was generated by Mendix Business Modeler 4.0.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package tests.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.UserAction;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import communitycommons.Misc;

/**
 * 
 */
public class multiplefile extends UserAction<Boolean>
{
	private IMendixObject __file;
	private tests.proxies.TestFile file;

	public multiplefile(IMendixObject file)
	{
		super();
		this.__file = file;
	}

	@Override
	public Boolean executeAction() throws Exception
	{
		this.file = __file == null ? null : tests.proxies.TestFile.initialize(getContext(), __file);

		// BEGIN USER CODE
		int size = (int) (long) Misc.getFileSize(getContext(), __file);
		byte[] buffer = new byte[size];
		IOUtils.read(Core.getFileDocumentContent(getContext(), __file), buffer);
		
		byte[] resultbuffer = new byte[size * file.getMultiplier()];
		
		//MWE: worst implementation ever, but thats not the point :)
		for(int i = 0; i < file.getMultiplier(); i++)
			for (int j = 0; j < size; j++)
				resultbuffer[i*size + j] = buffer[j];
		
		InputStream inputStream = new ByteArrayInputStream(resultbuffer);
		Core.storeFileDocumentContent(getContext(), __file, inputStream);
		file.commit();
		return true;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@Override
	public String toString()
	{
		return "multiplefile";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}