package maki325.bnha.api;

import java.io.Serializable;
import java.util.Locale;

import org.apache.commons.lang3.Validate;

import net.minecraft.util.ResourceLocation;

public class ResourceIdentifier implements Serializable {

	public static final long serialVersionUID = -9153729884384598866L;
	
	protected final String resourceDomain;
    protected final String resourcePath;

    public ResourceIdentifier(String resourceDomainIn, String resourcePathIn)
    {
        this.resourceDomain = resourceDomainIn;
        this.resourcePath = resourcePathIn;
    }

    /**
     * Splits an object name (such as minecraft:apple) into the domain and path parts and returns these as an array of
     * length 2. If no colon is present in the passed value the returned array will contain {null, toSplit}.
     */
    public static String[] splitObjectName(String toSplit)
    {
        String[] astring = new String[] {"minecraft", toSplit};
        int i = toSplit.indexOf(58);

        if (i >= 0)
        {
            astring[1] = toSplit.substring(i + 1, toSplit.length());

            if (i > 1)
            {
                astring[0] = toSplit.substring(0, i);
            }
        }

        return astring;
    }

    public String getResourcePath()
    {
        return this.resourcePath;
    }

    public String getResourceDomain()
    {
        return this.resourceDomain;
    }

    public String toString()
    {
        return this.resourceDomain + ':' + this.resourcePath;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof ResourceLocation))
        {
            return false;
        }
        else
        {
        	ResourceIdentifier resourcelocation = (ResourceIdentifier)p_equals_1_;
            return this.resourceDomain.equals(resourcelocation.resourceDomain) && this.resourcePath.equals(resourcelocation.resourcePath);
        }
    }
}
