RibbonMenu
==========

Two-Level Navigation menu for Android
Based on David Scott's [RibbonMenu](https://github.com/darvds/RibbonMenu)

![TwoLevelRibbonMenu](https://raw.github.com/mdelolmo/RibbonMenu/twoLevelMenu/device-2012-11-21-170206.png)


Usage
=====

Menus are created in xml as normal, adding text and an icon. They can be added dynamically and icons can be resources or regular Bitmaps.

In the layout you want to show the menu, add a FrameLayout as the root layout and add the RibbonMenuView set with width and height to match_parent.

In your class you need to implement the onTwoLevelRibbonMenuItemClick interface. This is called when you click a menu item and it passes the menu, the level abd the item id . You then make a reference to the RibbonMenuView and set the callback, set the menu items and open the level menu as it applies.

The sample activity shows how it should all work.


License
=======

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.