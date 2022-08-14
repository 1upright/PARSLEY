/*
Auto-generated by: https://github.com/pmndrs/gltfjsx
author: adorezooey (https://sketchfab.com/adorezooey)
license: CC-BY-4.0 (http://creativecommons.org/licenses/by/4.0/)
source: https://sketchfab.com/3d-models/plants-2744a7394966496c9dad1d1397a72569
title: plants
*/

import React, { useRef } from 'react'
import { useGLTF } from '@react-three/drei'

export function Model(props) {
  const { nodes, materials } = useGLTF('/scene.gltf')
  return (
    <group {...props} dispose={null}>
      <group rotation={[-Math.PI / 2, 0, 0]}>
        <mesh geometry={nodes.Object_2.geometry} material={materials['1coreopsi_1InfInt']} />
        <mesh geometry={nodes.Object_3.geometry} material={materials['1coreopsi_1Pdcel']} />
        <mesh geometry={nodes.Object_4.geometry} material={materials['1coreopsi_1Pdncle']} />
        <mesh geometry={nodes.Object_5.geometry} material={materials['1coreopsi_1Petal1']} />
        <mesh geometry={nodes.Object_6.geometry} material={materials['1coreopsi_1stLeaf']} />
        <mesh geometry={nodes.Object_7.geometry} material={materials['1coreopsi_1stPetiole']} />
        <mesh geometry={nodes.Object_8.geometry} material={materials['1coreopsi_Intrnd']} />
        <mesh geometry={nodes.Object_9.geometry} material={materials['1coreopsi_Leaf']} />
        <mesh geometry={nodes.Object_10.geometry} material={materials['1coreopsi_Petiole']} />
      </group>
    </group>
  )
}

useGLTF.preload('/scene.gltf')
