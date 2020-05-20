<templateSet group="Latex">
  <#list envs as e>
  <template name="\${e.command}" value="${e.snippet}" description="${e.command} environemnt"
            toReformat="false" toShortenFQNames="true">
    <variable name="EXPR" expression="" defaultValue="" alwaysStopAt="true"/>
    <context>
      <option name="AsciiDoc" value="true"/>
      <option name="LATEX" value="true"/>
    </context>
  </template>
  </#list>
</templateSet>