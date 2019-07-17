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
            <xsl:variable name="host" select="@host"/>
            <xsl:variable name="books" select="$doc//div[@id='all_votes']//a[@class='bookTitle']"/>
            <Book>
                <Name>
                    <xsl:value-of select="$doc//h1[@id='bookTitle']"/>
                </Name>
                <xsl:variable name="reviews" select="$doc//div[@class='friendReviews elementListBrown']"/>
                <xsl:variable name="authors" select="$doc//div[@id='bookAuthors']//a[@class='authorName']"/>
                <Author>
                    <xsl:for-each select="$authors">
                        <xsl:value-of select="./span"/>
                        <xsl:if test="position() != last()">
                            <xsl:text>, </xsl:text>
                        </xsl:if>
                    </xsl:for-each>
                </Author>
                <Score>
                    <xsl:value-of select="$doc//div[@id='bookMeta']//span[@itemprop='ratingValue']"/>
                </Score>
                <xsl:for-each select="$reviews">
                    <review>
                        <ReviewBy>
                            <xsl:value-of select=".//span[@itemprop='author']//a"/>
                        </ReviewBy>
                        <ReviewContent>
                            <xsl:for-each select=".//div[@class='reviewText stacked']/span/span">
                                <xsl:if test="position() = last()">
                                    <xsl:value-of select="."/>
                                </xsl:if>
                            </xsl:for-each>
                        </ReviewContent>
                        <Score>
                                <xsl:value-of select="count(.//span[@class=' staticStars notranslate']//span[@class='staticStar p10'])"/>
                        </Score>
                        <NumberOfLikes>
                            <xsl:variable name="likeStr" select=".//span[@class='likesCount']"/>
                            <xsl:value-of select="substring-before($likeStr, ' like')"/>
                        </NumberOfLikes>
                    </review>
                </xsl:for-each>
            </Book>
        </xsl:if>
        
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

