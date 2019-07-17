<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : metacritic.xsl
    Created on : July 2, 2019, 10:43 PM
    Author     : ASUS
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:xh="http://www.w3.org/1999/xhtml" 
                xmlns:t="http://trungtin719.tt/2019/xml"
                xmlns="http://trungtin719.tt/2019/xml"
                version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="t:metacritic">
        <xsl:variable name="doc" select="document(@link)"/>
        <xsl:variable name="host" select="@host"/>
        <xsl:variable name="filmLink" select="$doc//div[@class='friendReviews elementListBrown']//div[@class='reviewText stacked']//span"/>
        <FilmList>
            <page>
                <xsl:value-of select="count($doc//div[@class='friendReviews elementListBrown']//div[@class='reviewText stacked']//span)"/>
            </page>
            <xsl:for-each select="$filmLink">
                <film>
                    <name>
                        <xsl:value-of select="."/>
                    </name>
                </film>
            </xsl:for-each> 
        </FilmList>
        <!--<xsl:variable name="page" select="1"/>-->
<!--        <xsl:element name="page">
            <xsl:value-of select="$page"></xsl:value-of>
        </xsl:element>-->
<!--        <xsl:for-each select="$page">
            <counter>
                <xsl:value-of select="."/>
            </counter>
        </xsl:for-each>-->
    </xsl:template>
    

</xsl:stylesheet>
