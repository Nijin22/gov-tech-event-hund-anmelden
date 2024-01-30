package govTecEventSondernutzungStrasse

import de.seitenbau.serviceportal.scripting.api.v1.ScriptingApiV1

final List<String> expectedParameters = [
        "paymentUsername",
        "paymentPassword",
        "paymentAuthId",
        "paymentAuthPassword",
        "costInCent",
]

ScriptingApiV1 api = apiV1 // Variable is automatically set by Serviceportal process engine

expectedParameters.each { parameterName ->
  String parameterValue = api.getStartParameter().parameters.get(parameterName)
  if (parameterValue == null || parameterValue.isEmpty()) {
    throw new IllegalStateException("Failed to initialize process. Mandatory parameter '$parameterName' is not " +
            "available. Please add this parameter to the Jesaja system.")
  }
}

// Validate costInCent
String costInCent = api.getStartParameter().parameters.get("costInCent")
assert costInCent.matches(/\d+/): "Failed to initialize process. Parameter 'costInCent' was '${costInCent}', " +
        "but most only contain digits."
