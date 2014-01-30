// This file was generated by Mendix Business Modeler 4.0.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package restservices.actions;

import restservices.RestServices;
import com.mendix.systemwideinterfaces.core.UserAction;
import com.mendix.systemwideinterfaces.core.IMendixObject;

/**
 * 
 */
public class RebuildServiceIndex extends UserAction<Boolean>
{
	private IMendixObject __serviceDefinition;
	private restservices.proxies.ServiceDefinition serviceDefinition;

	public RebuildServiceIndex(IMendixObject serviceDefinition)
	{
		super();
		this.__serviceDefinition = serviceDefinition;
	}

	@Override
	public Boolean executeAction() throws Exception
	{
		this.serviceDefinition = __serviceDefinition == null ? null : restservices.proxies.ServiceDefinition.initialize(getContext(), __serviceDefinition);

		// BEGIN USER CODE
		RestServices.getService(serviceDefinition.getName()).getChangeManager().rebuildIndex();
		return true;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@Override
	public String toString()
	{
		return "RebuildServiceIndex";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}