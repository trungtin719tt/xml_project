<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : nhaxinh.xsl
    Created on : February 25, 2019, 10:16 PM
    Author     : Nguyen Thuy Ngoc
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:xh="http://www.w3.org/1999/xhtml"
                xmlns:t="http://ngocnt.com/2019/xml"
                xmlns="http://ngocnt.com/2019/xml"
                version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>

    <xsl:template match="t:nhaxinh">
        <xsl:variable name="host" select="@host"/>
        <xsl:variable name="doc" select="document(@link)"/>
        <xsl:variable name="roomLink" select="$doc//div[@id='menu']//ul//a"/>
        <Furniture>
            <xsl:for-each select="$roomLink">
                <room>
                    <name>
                        <xsl:value-of select="."/>
                    </name>
                    
                    <xsl:variable name="roomPage" select="document(concat($host, @href))"/>
                    <xsl:variable name="categoryLink" select="$roomPage//div[@id='room_left_menu']//a"/>
                    <xsl:for-each select="$categoryLink">
                        <category>
                            <name>
                                <xsl:value-of select="."/>
                            </name>
                            
                            <xsl:variable name="categoryPage" select="document(concat($host, @href))"/>
                            <xsl:variable name="productLink" select="$categoryPage//div[@id='product_catalog_list']/div"/>
                            
                            <xsl:for-each select="$productLink">
                                <product>
                                    <name>
                                        <xsl:value-of select="./h2"/>
                                    </name>
                                    <detail>
                                        <xsl:value-of select="concat($host, ./a/@href)"/>
                                    </detail>
                                    
                                    <xsl:call-template name="getNumber">
                                        <xsl:with-param name="str" select="./span"/> 
                                        <xsl:with-param name="elementName" select="'price'"/> 
                                    </xsl:call-template>
                                    <xsl:call-template name="productSize">
                                        <xsl:with-param name="size" select="translate(substring-after(substring-before(.//div[@class='toggle_info'], 'Vật liệu:'), 'Kích thước:'),'/','-')"/> 
                                    </xsl:call-template>
                                    
                                    <image>
                                        <xsl:value-of select="concat($host,./a/img/@src)"/>
                                    </image>
                                    <material>
                                        <xsl:value-of select="substring-after(.//div[@class='toggle_info'], 'Vật liệu:')"/>
                                    </material>
                                    
                                </product>
                            </xsl:for-each>
                        </category>
                    </xsl:for-each>
                </room>
            </xsl:for-each>
        </Furniture>
    </xsl:template>

    <xsl:template name="productSize">
        <xsl:param name="size"/>
        
        <xsl:choose>
            <xsl:when test="contains($size, '-')">
                <xsl:variable name="dimension" select="normalize-space(substring-before($size, '-'))"/>
                <xsl:call-template name="sizeItem">
                    <xsl:with-param name="dimension" select="$dimension"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="dimension" select="normalize-space($size)"/>
                <xsl:call-template name="sizeItem">
                    <xsl:with-param name="dimension" select="$dimension"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
        
        <xsl:variable name="nextItem" select="substring-after($size, '-')"/>
        <xsl:if test="boolean($nextItem)">
            <xsl:call-template name="productSize">
                <xsl:with-param name="size" select="$nextItem"/> 
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="not(boolean($nextItem))">
            <unit>
                <xsl:value-of select="substring-after(normalize-space($size), ' ')"/>
            </unit>
        </xsl:if>
        
    </xsl:template>
    
    <xsl:template name="sizeItem">
        <xsl:param name="dimension"/>
        
        <xsl:choose>
            <xsl:when test="contains($dimension, 'C')">
                <height>
                    <xsl:value-of select="translate($dimension, translate($dimension, '0123456789', ''), '')"/>
                </height>
            </xsl:when>
            <xsl:when test="contains($dimension, 'R')">
                <width>
                    <xsl:value-of select="translate($dimension, translate($dimension, '0123456789', ''), '')"/>
                </width>
            </xsl:when>
            <xsl:when test="contains($dimension, 'D')">
                <length>
                    <xsl:value-of select="translate($dimension, translate($dimension, '0123456789', ''), '')"/>
                </length>
            </xsl:when>
            <xsl:when test="contains($dimension, 'S')">
                <deep>
                    <xsl:value-of select="translate($dimension, translate($dimension, '0123456789', ''), '')"/>
                </deep>
            </xsl:when>
        </xsl:choose>
        
    </xsl:template>
    
    <xsl:template name="getNumber">
        <xsl:param name="str"/>
        <xsl:param name="elementName"/>

        <xsl:element name="{$elementName}">
            <xsl:value-of select="translate($str, translate($str, '0123456789', ''), '')"/>
        </xsl:element>
        
    </xsl:template>

</xsl:stylesheet>
