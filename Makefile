run-ldap:
	docker run --name openldap \
	  -p 389:389 \
	  -e LDAP_ORGANISATION="MyOrg" \
	  -e LDAP_DOMAIN="myorg.com" \
	  -e LDAP_ADMIN_PASSWORD=admin \
	  osixia/openldap:1.5.0