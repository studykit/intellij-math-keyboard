<#--noinspection ALL-->
<templateSet group="${name}">
  <#list envs as e>
  <template name="\${e.abbreation}" value="${e.snippet}" description="${e.name} environment" toReformat="false" toShortenFQNames="true">
    <variable name="EXPR" expression="" defaultValue="" alwaysStopAt="true"/>
    <context>
      <option name="ANY_OPENAPI_JSON_FILE" value="false" />
      <option name="ANY_OPENAPI_YAML_FILE" value="false" />
      <option name="CSS" value="false" />
      <option name="CUCUMBER_FEATURE_FILE" value="false" />
      <option name="Django" value="false" />
      <option name="ECMAScript6" value="false" />
      <option name="GENERAL_JSON_FILE" value="false" />
      <option name="GENERAL_YAML_FILE" value="false" />
      <option name="GROOVY" value="false" />
      <option name="HTML" value="false" />
      <option name="HTTP_CLIENT_ENVIRONMENT" value="false" />
      <option name="Handlebars" value="false" />
      <option name="JAVA_CODE" value="false" />
      <option name="JAVA_SCRIPT" value="false" />
      <option name="JSON" value="false" />
      <option name="JSP" value="false" />
      <option name="KOTLIN" value="false" />
      <option name="KUBERNETES_RESOURCE" value="false" />
      <option name="MAVEN" value="false" />
      <option name="OTHER" value="true" />
      <option name="PROTO" value="false" />
      <option name="PROTOTEXT" value="false" />
      <option name="Python" value="false" />
      <option name="R" value="false" />
      <option name="RBS_CODE" value="false" />
      <option name="REQUEST" value="false" />
      <option name="RHTML" value="false" />
      <option name="RUBY" value="false" />
      <option name="RUBY_COMMENTS" value="true" />
      <option name="SCALA" value="false" />
      <option name="SHELL_SCRIPT" value="false" />
      <option name="SQL" value="false" />
      <option name="SSP" value="false" />
      <option name="TypeScript" value="false" />
      <option name="UNIMATH" value="true" />
      <option name="XML" value="false" />
      <option name="sbt" value="false" />
    </context>
  </template>
  </#list>
  <#list cmds as c>
  <template name="\${c.abbreation}" value="${c.snippet}" description="${c.label}" toReformat="false" toShortenFQNames="true">
    <#list c.vars as var>
    <variable name="${var}" expression="" defaultValue="" alwaysStopAt="true"/>
    </#list>
    <context>
      <option name="ANY_OPENAPI_JSON_FILE" value="false" />
      <option name="ANY_OPENAPI_YAML_FILE" value="false" />
      <option name="CSS" value="false" />
      <option name="CUCUMBER_FEATURE_FILE" value="false" />
      <option name="Django" value="false" />
      <option name="ECMAScript6" value="false" />
      <option name="GENERAL_JSON_FILE" value="false" />
      <option name="GENERAL_YAML_FILE" value="false" />
      <option name="GROOVY" value="false" />
      <option name="HTML" value="false" />
      <option name="HTTP_CLIENT_ENVIRONMENT" value="false" />
      <option name="Handlebars" value="false" />
      <option name="JAVA_CODE" value="false" />
      <option name="JAVA_SCRIPT" value="false" />
      <option name="JSON" value="false" />
      <option name="JSP" value="false" />
      <option name="KOTLIN" value="false" />
      <option name="KUBERNETES_RESOURCE" value="false" />
      <option name="MAVEN" value="false" />
      <option name="OTHER" value="true" />
      <option name="PROTO" value="false" />
      <option name="PROTOTEXT" value="false" />
      <option name="Python" value="false" />
      <option name="R" value="false" />
      <option name="RBS_CODE" value="false" />
      <option name="REQUEST" value="false" />
      <option name="RHTML" value="false" />
      <option name="RUBY" value="false" />
      <option name="RUBY_COMMENTS" value="true" />
      <option name="SCALA" value="false" />
      <option name="SHELL_SCRIPT" value="false" />
      <option name="SQL" value="false" />
      <option name="SSP" value="false" />
      <option name="TypeScript" value="false" />
      <option name="UNIMATH" value="true" />
      <option name="XML" value="false" />
      <option name="sbt" value="false" />
    </context>
  </template>
</#list>
  <#if name == "Latex">
  <template name="\begin" value="\begin{$VAR$}&#10;  $TEXT$&#10;\end{$VAR$}&#10;$END$" description="begin environemnt" toReformat="false" toShortenFQNames="true">
    <variable name="VAR" expression="" defaultValue="" alwaysStopAt="true"/>
    <variable name="TEXT" expression="" defaultValue="" alwaysStopAt="true"/>
    <context>
      <option name="ANY_OPENAPI_JSON_FILE" value="false" />
      <option name="ANY_OPENAPI_YAML_FILE" value="false" />
      <option name="CSS" value="false" />
      <option name="CUCUMBER_FEATURE_FILE" value="false" />
      <option name="Django" value="false" />
      <option name="ECMAScript6" value="false" />
      <option name="GENERAL_JSON_FILE" value="false" />
      <option name="GENERAL_YAML_FILE" value="false" />
      <option name="GROOVY" value="false" />
      <option name="HTML" value="false" />
      <option name="HTTP_CLIENT_ENVIRONMENT" value="false" />
      <option name="Handlebars" value="false" />
      <option name="JAVA_CODE" value="false" />
      <option name="JAVA_SCRIPT" value="false" />
      <option name="JSON" value="false" />
      <option name="JSP" value="false" />
      <option name="KOTLIN" value="false" />
      <option name="KUBERNETES_RESOURCE" value="false" />
      <option name="MAVEN" value="false" />
      <option name="OTHER" value="true" />
      <option name="PROTO" value="false" />
      <option name="PROTOTEXT" value="false" />
      <option name="Python" value="false" />
      <option name="R" value="false" />
      <option name="RBS_CODE" value="false" />
      <option name="REQUEST" value="false" />
      <option name="RHTML" value="false" />
      <option name="RUBY" value="false" />
      <option name="RUBY_COMMENTS" value="true" />
      <option name="SCALA" value="false" />
      <option name="SHELL_SCRIPT" value="false" />
      <option name="SQL" value="false" />
      <option name="SSP" value="false" />
      <option name="TypeScript" value="false" />
      <option name="UNIMATH" value="true" />
      <option name="XML" value="false" />
      <option name="sbt" value="false" />
    </context>
  </template>
  <template name="\llbracket" value="\llbracket $TEXT$ \rrbracket $END$" description="⟦ ... ⟧" toReformat="false" toShortenFQNames="true">
    <variable name="TEXT" expression="" defaultValue="" alwaysStopAt="true"/>
    <context>
      <option name="ANY_OPENAPI_JSON_FILE" value="false" />
      <option name="ANY_OPENAPI_YAML_FILE" value="false" />
      <option name="CSS" value="false" />
      <option name="CUCUMBER_FEATURE_FILE" value="false" />
      <option name="Django" value="false" />
      <option name="ECMAScript6" value="false" />
      <option name="GENERAL_JSON_FILE" value="false" />
      <option name="GENERAL_YAML_FILE" value="false" />
      <option name="GROOVY" value="false" />
      <option name="HTML" value="false" />
      <option name="HTTP_CLIENT_ENVIRONMENT" value="false" />
      <option name="Handlebars" value="false" />
      <option name="JAVA_CODE" value="false" />
      <option name="JAVA_SCRIPT" value="false" />
      <option name="JSON" value="false" />
      <option name="JSP" value="false" />
      <option name="KOTLIN" value="false" />
      <option name="KUBERNETES_RESOURCE" value="false" />
      <option name="MAVEN" value="false" />
      <option name="OTHER" value="true" />
      <option name="PROTO" value="false" />
      <option name="PROTOTEXT" value="false" />
      <option name="Python" value="false" />
      <option name="R" value="false" />
      <option name="RBS_CODE" value="false" />
      <option name="REQUEST" value="false" />
      <option name="RHTML" value="false" />
      <option name="RUBY" value="false" />
      <option name="RUBY_COMMENTS" value="true" />
      <option name="SCALA" value="false" />
      <option name="SHELL_SCRIPT" value="false" />
      <option name="SQL" value="false" />
      <option name="SSP" value="false" />
      <option name="TypeScript" value="false" />
      <option name="UNIMATH" value="true" />
      <option name="XML" value="false" />
      <option name="sbt" value="false" />
    </context>
  </template>
  <template name="\xquad" value="\xquad $END$" description="" toReformat="false" toShortenFQNames="true">
    <context>
      <option name="ANY_OPENAPI_JSON_FILE" value="false" />
      <option name="ANY_OPENAPI_YAML_FILE" value="false" />
      <option name="CSS" value="false" />
      <option name="CUCUMBER_FEATURE_FILE" value="false" />
      <option name="Django" value="false" />
      <option name="ECMAScript6" value="false" />
      <option name="GENERAL_JSON_FILE" value="false" />
      <option name="GENERAL_YAML_FILE" value="false" />
      <option name="GROOVY" value="false" />
      <option name="HTML" value="false" />
      <option name="HTTP_CLIENT_ENVIRONMENT" value="false" />
      <option name="Handlebars" value="false" />
      <option name="JAVA_CODE" value="false" />
      <option name="JAVA_SCRIPT" value="false" />
      <option name="JSON" value="false" />
      <option name="JSP" value="false" />
      <option name="KOTLIN" value="false" />
      <option name="KUBERNETES_RESOURCE" value="false" />
      <option name="MAVEN" value="false" />
      <option name="OTHER" value="true" />
      <option name="PROTO" value="false" />
      <option name="PROTOTEXT" value="false" />
      <option name="Python" value="false" />
      <option name="R" value="false" />
      <option name="RBS_CODE" value="false" />
      <option name="REQUEST" value="false" />
      <option name="RHTML" value="false" />
      <option name="RUBY" value="false" />
      <option name="RUBY_COMMENTS" value="true" />
      <option name="SCALA" value="false" />
      <option name="SHELL_SCRIPT" value="false" />
      <option name="SQL" value="false" />
      <option name="SSP" value="false" />
      <option name="TypeScript" value="false" />
      <option name="UNIMATH" value="true" />
      <option name="XML" value="false" />
      <option name="sbt" value="false" />
    </context>
  </template>
  </#if>
</templateSet>
