/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.fury.serializer.kotlin;

import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import org.apache.fury.AbstractThreadSafeFury;
import org.apache.fury.Fury;
import org.apache.fury.ThreadSafeFury;
import org.apache.fury.resolver.ClassResolver;
import org.apache.fury.serializer.collection.CollectionSerializers;
import org.apache.fury.serializer.collection.MapSerializers;


/**
 * KotlinSerializers provide default serializers for kotlin.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class KotlinSerializers {

  public static void registerSerializers(ThreadSafeFury fury) {
    AbstractThreadSafeFury threadSafeFury = (AbstractThreadSafeFury) fury;
    threadSafeFury.registerCallback(KotlinSerializers::registerSerializers);
  }

    public static void registerSerializers(Fury fury) {
        ClassResolver resolver = fury.getClassResolver();

        // UByte
        Class ubyteClass = KotlinToJavaClass.INSTANCE.getUByteClass();
        resolver.register(ubyteClass);
        resolver.registerSerializer(ubyteClass, new UByteSerializer(fury));

        // UShort
        Class ushortClass = KotlinToJavaClass.INSTANCE.getUShortClass();
        resolver.register(ushortClass);
        resolver.registerSerializer(ushortClass, new UShortSerializer(fury));

        // UInt
        Class uintClass = KotlinToJavaClass.INSTANCE.getUIntClass();
        resolver.register(uintClass);
        resolver.registerSerializer(uintClass, new UIntSerializer(fury));

        // ULong
        Class ulongClass = KotlinToJavaClass.INSTANCE.getULongClass();
        resolver.register(ulongClass);
        resolver.registerSerializer(ulongClass, new ULongSerializer(fury));

        // EmptyList
        Class emptyListClass = KotlinToJavaClass.INSTANCE.getEmptyListClass();
        resolver.register(emptyListClass);
        resolver.registerSerializer(emptyListClass, new CollectionSerializers.EmptyListSerializer(fury, emptyListClass));

        // EmptySet
        Class emptySetClass = KotlinToJavaClass.INSTANCE.getEmptySetClass();
        resolver.register(emptySetClass);
        resolver.registerSerializer(emptySetClass, new CollectionSerializers.EmptySetSerializer(fury, emptySetClass));

        // EmptyMap
        Class emptyMapClass = KotlinToJavaClass.INSTANCE.getEmptyMapClass();
        resolver.register(emptyMapClass);
        resolver.registerSerializer(emptyMapClass, new MapSerializers.EmptyMapSerializer(fury, emptyMapClass));

        // Non-Java collection implementation in kotlin stdlib.
        Class arrayDequeClass = KotlinToJavaClass.INSTANCE.getArrayDequeClass();
        resolver.register(arrayDequeClass);
        resolver.registerSerializer(arrayDequeClass, new KotlinArrayDequeSerializer(fury, arrayDequeClass));

        // Unsigned array classes: UByteArray, UShortArray, UIntArray, ULongArray.
        resolver.register(UByteArray.class);
        resolver.registerSerializer(UByteArray.class, new UByteArraySerializer(fury));
        resolver.register(UShortArray.class);
        resolver.registerSerializer(UShortArray.class, new UShortArraySerializer(fury));
        resolver.register(UIntArray.class);
        resolver.registerSerializer(UIntArray.class, new UIntArraySerializer(fury));
        resolver.register(ULongArray.class);
        resolver.registerSerializer(ULongArray.class, new ULongArraySerializer(fury));
    }
}
