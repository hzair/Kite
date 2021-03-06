/**
 *
 */
package com.groupeseb.kite.function.impl;

import com.groupeseb.kite.ContextProcessor;
import com.groupeseb.kite.exceptions.NotYetSupportedException;
import org.json.simple.JSONObject;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Abstract function which extract Object from CreationLog and allows
 * subclasses to customize result before returning it.
 */
public abstract class ObjectFunction extends AbstractWithOneParameter {

	protected ObjectFunction(String name) {
		super(name);
	}

	@Override
	public String apply(String parameter, ContextProcessor context) {
		Object untransformedJsonObject = checkNotNull(context.getKiteContext().getObjectVariable(parameter),
				"No object corresponds to variable named [%s], do you have the corresponding \"objectVariables\" section in your test?",
				parameter);

		if (untransformedJsonObject instanceof JSONObject) {
			return innerApplyOnObject((JSONObject) untransformedJsonObject, context);
		}
		if (untransformedJsonObject instanceof String) {
			return context.processPlaceholdersInString((String) untransformedJsonObject);
		}

		throw new NotYetSupportedException("Class not yet supported in ObjectAsStringFunction "
				+ untransformedJsonObject.getClass().getSimpleName());
	}

	protected abstract String innerApplyOnObject(JSONObject untransformedJsonObject, ContextProcessor context);

}
