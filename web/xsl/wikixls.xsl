<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : wikixls.xsl
    Created on : June 22, 2019, 9:16 AM
    Author     : ASUS
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:xh="http://www.w3.org/1999/xhtml" 
                xmlns:t="http://techeco.net/2017/xml"
                xmlns="http://tttecheco.net/2017/xml"
                version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="t:plants">
        <xsl:variable name="listDoc" select="document(@link)"/>
        <xsl:variable name="host" select="document(@host)"/>
        <xsl:element name="plants">
            <xsl:for-each select="$listDoc//xh:div[@class='mw-category-group']//xh:li/xh:a">
                <xsl:variable name="pName" select="normalize-space(text())"/>
                <xsl:element name="plant">
                    <xsl:attribute name="name">
                        <xsl:value-of select="$pName"/>
                    </xsl:attribute>
                    <xsl:attribute name="link">
                        <xsl:value-of select="@href"/>
                    </xsl:attribute>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
