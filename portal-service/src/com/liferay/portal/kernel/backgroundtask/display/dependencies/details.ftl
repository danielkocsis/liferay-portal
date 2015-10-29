<div class="alert ">
	<h4>hi</h4>
	<span class="error-message">
		<liferay-ui:message key="message"/>
	</span>

	<#assign info = "text" >
	<ul class="error-list-items">
		<li>
			${info}

			<#if info?? >
				<span class="error-info">(${htmlUtil.escape(info)})</span>
			</#if>
		</li>
	</ul>
</div>