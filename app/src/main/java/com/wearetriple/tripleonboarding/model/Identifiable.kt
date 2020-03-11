package com.wearetriple.tripleonboarding.model

const val UNSET_ID = -1

/**
 * A template used for entities coming from the database which also share the same attributes.
 */
open class Identifiable(var key: Int = UNSET_ID)