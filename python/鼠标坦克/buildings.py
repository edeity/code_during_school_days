#_*_coding:utf-8 _*_
'''
@author: javer
'''
'''连接到外面的世界'''
from substance import *
import image_factory
#    --------------------------------我是华丽的分割线------------------------------------------------------------------------------------
'''建筑图片地址'''
long_wall_image_filename = "image/buildings/long_wall.jpg"
thin_wall_image_filename = 'image/buildings/thin_wall.jpg'
short_wall_image_filename = 'image/buildings/short_wall.jpg'
wall_image_filename = 'image/buildings/wall.jpg'
little_wall_image_filename = 'image/buildings/little_wall.jpg'
accelerate_bar_image_filename = 'image/buildings/accelerate_bar.jpg'
'''图片处理器'''
image_factory = image_factory.ImageFactory()
'''装载建筑图片'''
long_wall = image_factory.image_process(long_wall_image_filename, 1)
thin_wall = image_factory.image_process(thin_wall_image_filename, 1)
short_wall =  image_factory.image_process(short_wall_image_filename, 1)
wall = image_factory.image_process(wall_image_filename, 1)
little_wall = image_factory.image_process(little_wall_image_filename, 1)
accelerate_bar =  image_factory.image_process(accelerate_bar_image_filename, 1)
#    --------------------------------我是华丽的分割线------------------------------------------------------------------------------------
class  WorldBridge():
    proManager = None
    Substance = None
    def __init__(self, proManager):
            WorldBridge.proManager = proManager   

class Building(Substance):      #抽象建筑
    def __init__(self, iamges):
        Substance.__init__(self, WorldBridge.proManager.buildings, iamges)
        self.name = "building"
        self.life = 1
        self.ap = 1
        self.armor = 2
    def out_of_border(self):#不需要调用该方法
        pass
class AccelerateBar(Building):
    def __init__(self, position):
        Building.__init__(self, accelerate_bar)
        self.name = 'accelerate_bar'
        self.position = position
    def effect(self, effect_object):
        effect_object.statu = 3
        effect_object.last_time = 8     
        
class LongWall(Building):           #建筑:城墙  width 50
    def __init__(self, position):
        Building.__init__(self, long_wall)
        #自身属性
        self.position = position
        self.ap = 2
        self.name = 'long_wall' 
class ThinWall(Building):           #建筑:城墙   width 20
    def __init__(self, position):
        Building.__init__(self, thin_wall)
        #自身属性
        self.position = position
        self.ap = 2
        self.name = 'thin_wall' 
class ShortWall(Building):
    def __init__(self, position):
        Building.__init__(self, short_wall)
        #自身属性
        self.position = position
        self.ap = 2
        self.name = 'short_wall' 
class Wall(Building):
    def __init__(self, position):
        Building.__init__(self, wall)
        #自身属性
        self.position = position
        self.ap = 2
        self.name = 'wall' 
class LittleWall(Building):
    def __init__(self, position):
        Building.__init__(self, little_wall)
        #自身属性
        self.position = position
        self.ap = 2
        self.name = 'little_wall' 