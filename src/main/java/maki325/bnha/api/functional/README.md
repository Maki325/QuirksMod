# Functional Package



### Function.java

A functional **serializable** interface that adds a function that can be transmitted over internet with the new **Network Message System**. This function is used to create lambdas.

```java
void accept(NBTTagCompound data, World world, BlockPos pos, EntityPlayer player);
```

It uses NBTTagCompound, World, BlockPos and EntityPlayer to run a lambda function on the receiving end.