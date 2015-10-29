// This file was generated by Mendix Business Modeler.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package restservices.actions;

import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import restservices.consume.RestConsumer;
import com.mendix.systemwideinterfaces.core.IMendixObject;

/**
 * 
 */
public class postStringDataWithResult extends CustomJavaAction<IMendixObject>
{
	private String collectionUrl;
	private String dataString;
	private IMendixObject responseData;

	public postStringDataWithResult(IContext context, String collectionUrl, String dataString, IMendixObject responseData)
	{
		super(context);
		this.collectionUrl = collectionUrl;
		this.dataString = dataString;
		this.responseData = responseData;
	}

	@Override
	public IMendixObject executeAction() throws Exception
	{
		// BEGIN USER CODE
		return RestConsumer.postObject(getContext(), collectionUrl, null, dataString, responseData).getMendixObject();
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@Override
	public String toString()
	{
		return "postStringDataWithResult";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}