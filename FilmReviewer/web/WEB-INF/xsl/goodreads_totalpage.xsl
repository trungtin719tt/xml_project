<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : goodreads.xsl
    Created on : July 7, 2019, 9:08 AM
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
    <xsl:template match="t:source">
        <xsl:if test="boolean(document(@link))">
            <xsl:variable name="doc" select="document(@link)"/>
            <xsl:variable name="pagelinks" select="($doc//div[@id='all_votes']//div[@class='pagination']//a[not(@class='next_page')])[last()]"/>
            <TotalPage>
                <!--<xsl:value-of select="$doc//div[@id='all_votes']//div[@class='pagination']//a[@class != 'next_page']"/>-->
                <NumOfPage>
                    <xsl:value-of select="$pagelinks"/>
                </NumOfPage>
<!--                <xsl:for-each select="$pagelinks">
                    <count>
                        <xsl:value-of select="."/>
                    </count>
                    <xsl:if test="position() = last()">
                        <NumOfPage>
                            <xsl:value-of select="."/>
                        </NumOfPage>
                        
                    </xsl:if>
                </xsl:for-each>-->
            </TotalPage>
            
        </xsl:if>
    </xsl:template>
    

</xsl:stylesheet>

