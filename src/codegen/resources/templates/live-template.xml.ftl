<#--noinspection ALL-->
<templateSet group="${name}">
  <#list envs as e>
  <template name="\${e.command}" value="${e.snippet}" description="${e.command} environemnt" toReformat="false" toShortenFQNames="true">
    <variable name="EXPR" expression="" defaultValue="" alwaysStopAt="true"/>
    <context>
      <option name="UNIMATH" value="true"/>
      <option name="OTHER" value="true" />
      <option name="ASPECTJ" value="false" />
      <option name="CSS" value="false" />
      <option name="CUCUMBER_FEATURE_FILE" value="false" />
      <option name="CoffeeScript" value="false" />
      <option name="Django" value="false" />
      <option name="ES6_CLASS" value="false" />
      <option name="ES6_EXPRESSION" value="false" />
      <option name="ES6_STATEMENT" value="false" />
      <option name="GROOVY_DECLARATION" value="false" />
      <option name="GROOVY_EXPRESSION" value="false" />
      <option name="GROOVY_STATEMENT" value="false" />
      <option name="HTML" value="false" />
      <option name="HTML_TEXT" value="true" />
      <option name="JAVA_CODE" value="false" />
      <option name="JAVA_COMMENT" value="true" />
      <option name="JSON_PROPERTY_KEYS" value="false" />
      <option name="JSON_STRING_VALUES" value="false" />
      <option name="JSP" value="false" />
      <option name="JSX_HTML" value="false" />
      <option name="JS_CLASS" value="false" />
      <option name="JS_EXPRESSION" value="false" />
      <option name="JS_STATEMENT" value="false" />
      <option name="KOTLIN_CLASS" value="false" />
      <option name="KOTLIN_EXPRESSION" value="false" />
      <option name="KOTLIN_OBJECT_DECLARATION" value="false" />
      <option name="KOTLIN_STATEMENT" value="false" />
      <option name="KOTLIN_TOPLEVEL" value="false" />
      <option name="KUBERNETES_RESOURCE" value="false" />
      <option name="MAVEN" value="false" />
      <option name="Python_Class" value="false" />
      <option name="R" value="false" />
      <option name="REQUEST" value="false" />
      <option name="RHTML" value="false" />
      <option name="RUBY_CODE" value="false" />
      <option name="RUBY_STRING" value="false" />
      <option name="SCALA" value="false" />
      <option name="SCALA_COMMENT" value="true" />
      <option name="SHELL_SCRIPT" value="false" />
      <option name="SQL" value="false" />
      <option name="SSP" value="false" />
      <option name="TS_CLASS" value="false" />
      <option name="TS_EXPRESSION" value="false" />
      <option name="TS_STATEMENT" value="false" />
      <option name="sbt" value="false" />
    </context>
  </template>
  </#list>
  <#list cmds as c>
  <template name="\${c.command}" value="${c.snippet}" description="${c.label}" toReformat="false" toShortenFQNames="true">
    <#list c.vars as var>
    <variable name="${var}" expression="" defaultValue="" alwaysStopAt="true"/>
    </#list>
    <context>
      <option name="UNIMATH" value="true"/>
      <option name="OTHER" value="true" />
      <option name="ASPECTJ" value="false" />
      <option name="CSS" value="false" />
      <option name="CUCUMBER_FEATURE_FILE" value="false" />
      <option name="CoffeeScript" value="false" />
      <option name="Django" value="false" />
      <option name="ES6_CLASS" value="false" />
      <option name="ES6_EXPRESSION" value="false" />
      <option name="ES6_STATEMENT" value="false" />
      <option name="GROOVY_DECLARATION" value="false" />
      <option name="GROOVY_EXPRESSION" value="false" />
      <option name="GROOVY_STATEMENT" value="false" />
      <option name="HTML" value="false" />
      <option name="HTML_TEXT" value="true" />
      <option name="JAVA_CODE" value="false" />
      <option name="JAVA_COMMENT" value="true" />
      <option name="JSON_PROPERTY_KEYS" value="false" />
      <option name="JSON_STRING_VALUES" value="false" />
      <option name="JSP" value="false" />
      <option name="JSX_HTML" value="false" />
      <option name="JS_CLASS" value="false" />
      <option name="JS_EXPRESSION" value="false" />
      <option name="JS_STATEMENT" value="false" />
      <option name="KOTLIN_CLASS" value="false" />
      <option name="KOTLIN_EXPRESSION" value="false" />
      <option name="KOTLIN_OBJECT_DECLARATION" value="false" />
      <option name="KOTLIN_STATEMENT" value="false" />
      <option name="KOTLIN_TOPLEVEL" value="false" />
      <option name="KUBERNETES_RESOURCE" value="false" />
      <option name="MAVEN" value="false" />
      <option name="Python_Class" value="false" />
      <option name="R" value="false" />
      <option name="REQUEST" value="false" />
      <option name="RHTML" value="false" />
      <option name="RUBY_CODE" value="false" />
      <option name="RUBY_STRING" value="false" />
      <option name="SCALA" value="false" />
      <option name="SCALA_COMMENT" value="true" />
      <option name="SHELL_SCRIPT" value="false" />
      <option name="SQL" value="false" />
      <option name="SSP" value="false" />
      <option name="TS_CLASS" value="false" />
      <option name="TS_EXPRESSION" value="false" />
      <option name="TS_STATEMENT" value="false" />
      <option name="sbt" value="false" />
    </context>
  </template>
</#list>
  <template name="\begin" value="\begin{$VAR$}&#10;  $TEXT$&#10;\end{$VAR$}&#10;$END$" description="begin environemnt" toReformat="false" toShortenFQNames="true">
    <variable name="VAR" expression="" defaultValue="" alwaysStopAt="true"/>
    <variable name="TEXT" expression="" defaultValue="" alwaysStopAt="true"/>
    <context>
      <option name="UNIMATH" value="true"/>
      <option name="OTHER" value="true" />
      <option name="ASPECTJ" value="false" />
      <option name="CSS" value="false" />
      <option name="CUCUMBER_FEATURE_FILE" value="false" />
      <option name="CoffeeScript" value="false" />
      <option name="Django" value="false" />
      <option name="ES6_CLASS" value="false" />
      <option name="ES6_EXPRESSION" value="false" />
      <option name="ES6_STATEMENT" value="false" />
      <option name="GROOVY_DECLARATION" value="false" />
      <option name="GROOVY_EXPRESSION" value="false" />
      <option name="GROOVY_STATEMENT" value="false" />
      <option name="HTML" value="false" />
      <option name="HTML_TEXT" value="true" />
      <option name="JAVA_CODE" value="false" />
      <option name="JAVA_COMMENT" value="true" />
      <option name="JSON_PROPERTY_KEYS" value="false" />
      <option name="JSON_STRING_VALUES" value="false" />
      <option name="JSP" value="false" />
      <option name="JSX_HTML" value="false" />
      <option name="JS_CLASS" value="false" />
      <option name="JS_EXPRESSION" value="false" />
      <option name="JS_STATEMENT" value="false" />
      <option name="KOTLIN_CLASS" value="false" />
      <option name="KOTLIN_EXPRESSION" value="false" />
      <option name="KOTLIN_OBJECT_DECLARATION" value="false" />
      <option name="KOTLIN_STATEMENT" value="false" />
      <option name="KOTLIN_TOPLEVEL" value="false" />
      <option name="KUBERNETES_RESOURCE" value="false" />
      <option name="MAVEN" value="false" />
      <option name="Python_Class" value="false" />
      <option name="R" value="false" />
      <option name="REQUEST" value="false" />
      <option name="RHTML" value="false" />
      <option name="RUBY_CODE" value="false" />
      <option name="RUBY_STRING" value="false" />
      <option name="SCALA" value="false" />
      <option name="SCALA_COMMENT" value="true" />
      <option name="SHELL_SCRIPT" value="false" />
      <option name="SQL" value="false" />
      <option name="SSP" value="false" />
      <option name="TS_CLASS" value="false" />
      <option name="TS_EXPRESSION" value="false" />
      <option name="TS_STATEMENT" value="false" />
      <option name="sbt" value="false" />
    </context>
  </template>
  <template name="\llbracket" value="\llbracket $TEXT$ \rrbracket $END$" description="⟦ ... ⟧" toReformat="false" toShortenFQNames="true">
    <variable name="TEXT" expression="" defaultValue="" alwaysStopAt="true"/>
    <context>
      <option name="UNIMATH" value="true"/>
      <option name="OTHER" value="true" />
      <option name="ASPECTJ" value="false" />
      <option name="CSS" value="false" />
      <option name="CUCUMBER_FEATURE_FILE" value="false" />
      <option name="CoffeeScript" value="false" />
      <option name="Django" value="false" />
      <option name="ES6_CLASS" value="false" />
      <option name="ES6_EXPRESSION" value="false" />
      <option name="ES6_STATEMENT" value="false" />
      <option name="GROOVY_DECLARATION" value="false" />
      <option name="GROOVY_EXPRESSION" value="false" />
      <option name="GROOVY_STATEMENT" value="false" />
      <option name="HTML" value="false" />
      <option name="HTML_TEXT" value="true" />
      <option name="JAVA_CODE" value="false" />
      <option name="JAVA_COMMENT" value="true" />
      <option name="JSON_PROPERTY_KEYS" value="false" />
      <option name="JSON_STRING_VALUES" value="false" />
      <option name="JSP" value="false" />
      <option name="JSX_HTML" value="false" />
      <option name="JS_CLASS" value="false" />
      <option name="JS_EXPRESSION" value="false" />
      <option name="JS_STATEMENT" value="false" />
      <option name="KOTLIN_CLASS" value="false" />
      <option name="KOTLIN_EXPRESSION" value="false" />
      <option name="KOTLIN_OBJECT_DECLARATION" value="false" />
      <option name="KOTLIN_STATEMENT" value="false" />
      <option name="KOTLIN_TOPLEVEL" value="false" />
      <option name="KUBERNETES_RESOURCE" value="false" />
      <option name="MAVEN" value="false" />
      <option name="Python_Class" value="false" />
      <option name="R" value="false" />
      <option name="REQUEST" value="false" />
      <option name="RHTML" value="false" />
      <option name="RUBY_CODE" value="false" />
      <option name="RUBY_STRING" value="false" />
      <option name="SCALA" value="false" />
      <option name="SCALA_COMMENT" value="true" />
      <option name="SHELL_SCRIPT" value="false" />
      <option name="SQL" value="false" />
      <option name="SSP" value="false" />
      <option name="TS_CLASS" value="false" />
      <option name="TS_EXPRESSION" value="false" />
      <option name="TS_STATEMENT" value="false" />
      <option name="sbt" value="false" />
    </context>
  </template>
  <template name="\xquad" value="\xquad $END$" description="" toReformat="false" toShortenFQNames="true">
    <context>
      <option name="UNIMATH" value="true"/>
      <option name="OTHER" value="true" />
      <option name="ASPECTJ" value="false" />
      <option name="CSS" value="false" />
      <option name="CUCUMBER_FEATURE_FILE" value="false" />
      <option name="CoffeeScript" value="false" />
      <option name="Django" value="false" />
      <option name="ES6_CLASS" value="false" />
      <option name="ES6_EXPRESSION" value="false" />
      <option name="ES6_STATEMENT" value="false" />
      <option name="GROOVY_DECLARATION" value="false" />
      <option name="GROOVY_EXPRESSION" value="false" />
      <option name="GROOVY_STATEMENT" value="false" />
      <option name="HTML" value="false" />
      <option name="HTML_TEXT" value="true" />
      <option name="JAVA_CODE" value="false" />
      <option name="JAVA_COMMENT" value="true" />
      <option name="JSON_PROPERTY_KEYS" value="false" />
      <option name="JSON_STRING_VALUES" value="false" />
      <option name="JSP" value="false" />
      <option name="JSX_HTML" value="false" />
      <option name="JS_CLASS" value="false" />
      <option name="JS_EXPRESSION" value="false" />
      <option name="JS_STATEMENT" value="false" />
      <option name="KOTLIN_CLASS" value="false" />
      <option name="KOTLIN_EXPRESSION" value="false" />
      <option name="KOTLIN_OBJECT_DECLARATION" value="false" />
      <option name="KOTLIN_STATEMENT" value="false" />
      <option name="KOTLIN_TOPLEVEL" value="false" />
      <option name="KUBERNETES_RESOURCE" value="false" />
      <option name="MAVEN" value="false" />
      <option name="Python_Class" value="false" />
      <option name="R" value="false" />
      <option name="REQUEST" value="false" />
      <option name="RHTML" value="false" />
      <option name="RUBY_CODE" value="false" />
      <option name="RUBY_STRING" value="false" />
      <option name="SCALA" value="false" />
      <option name="SCALA_COMMENT" value="true" />
      <option name="SHELL_SCRIPT" value="false" />
      <option name="SQL" value="false" />
      <option name="SSP" value="false" />
      <option name="TS_CLASS" value="false" />
      <option name="TS_EXPRESSION" value="false" />
      <option name="TS_STATEMENT" value="false" />
      <option name="sbt" value="false" />
    </context>
  </template>
</templateSet>
