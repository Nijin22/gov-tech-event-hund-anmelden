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
assert api?.startParameter?.leistung?.id != null: "Failed to initiate process. Leika-ID required but was not " +
        "provided. Please ensure that the process is provided a Leika-ID via process-start-parameters."
assert api?.startParameter?.organisationseinheit?.id != null: "Failed to initiate process. OrgUnit-ID required but " +
        "was not provided. Please ensure that the process is provided a OrgUnit-ID via process-start-parameters."
assert api.getStartParameter().parameters != null: "Failed to initiate process. Process parameters are not present. " +
        "Please ensure least one parameter is configured in Jesaja."

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
