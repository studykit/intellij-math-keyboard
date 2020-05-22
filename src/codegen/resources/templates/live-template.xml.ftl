<#--noinspection ALL-->
<templateSet group="${name}">
  <#list envs as e>
  <template name="\${e.abbreation}" value="${e.snippet}" description="${e.name} environment" toReformat="false" toShortenFQNames="true">
    <variable name="EXPR" expression="" defaultValue="" alwaysStopAt="true"/>
    <context>
      <option name="UNIMATH" value="true"/>
    </context>
  </template>
  </#list>
  <#list cmds as c>
  <template name="\${c.abbreation}" value="${c.snippet}" description="${c.label}" toReformat="false" toShortenFQNames="true">
    <#list c.vars as var>
    <variable name="${var}" expression="" defaultValue="" alwaysStopAt="true"/>
    </#list>
    <context>
      <option name="UNIMATH" value="true"/>
    </context>
  </template>
</#list>
  <#if name == "Latex">
  <template name="\begin" value="\begin{$VAR$}&#10;  $TEXT$&#10;\end{$VAR$}&#10;$END$" description="begin environemnt" toReformat="false" toShortenFQNames="true">
    <variable name="VAR" expression="" defaultValue="" alwaysStopAt="true"/>
    <variable name="TEXT" expression="" defaultValue="" alwaysStopAt="true"/>
    <context>
      <option name="UNIMATH" value="true"/>
    </context>
  </template>
  <template name="\llbracket" value="\llbracket $TEXT$ \rrbracket $END$" description="⟦ ... ⟧" toReformat="false" toShortenFQNames="true">
    <variable name="TEXT" expression="" defaultValue="" alwaysStopAt="true"/>
    <context>
      <option name="UNIMATH" value="true"/>
    </context>
  </template>
  <template name="\xquad" value="\xquad $END$" description="" toReformat="false" toShortenFQNames="true">
    <context>
      <option name="UNIMATH" value="true"/>
    </context>
  </template>
  </#if>
</templateSet>
