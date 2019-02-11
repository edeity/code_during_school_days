#_*_coding:utf-8_*_
'''
Created on 2013年9月22日

@author: javer
'''
'''连接到外面的世界'''
from substance import *
import image_factory
#    --------------------------------我是华丽的分割线------------------------------------------------------------------------------------
'''图片处理器'''
image_factory = image_factory.ImageFactory()
'''武器图片文件'''
shell_image_filename = 'image/weapons/simpleshell.png'
ice_shell_image_filename = 'image/weapons/iceShell.png'
mine_image_filename = 'image/weapons/mine.png'
special_shell_image_filename = 'image/weapons/specialShell.png'
tracking_image_filename = 'image/weapons/tracking.png'
'''装载子弹图片'''
simple_shell_image = image_factory.image_process(shell_image_filename, 7)
ice_shell_image = image_factory.image_process(ice_shell_image_filename, 1)
mine_image = image_factory.image_process(mine_image_filename, 4)
specila_shell_image = image_factory.image_process(special_shell_image_filename, 1)
tracking_image =  image_factory.image_process(tracking_image_filename, 4)
#egg
egg_image_filename = 'image/weapons/eggs/egg.png'
egg_image =  image_factory.image_process(egg_image_filename, 4)
#    --------------------------------我是华丽的分割线------------------------------------------------------------------------------------
class  WorldBridge():
    proManager = None
    Substance = None
    def __init__(self, proManager):
            WorldBridge.proManager = proManager   
            
class Weapon(Substance):    #抽象武器,抽象武器都没有伤害
    def __init__(self, substance, images):
        Substance.__init__(self, WorldBridge.proManager.weapons, images)
        self.ap = 1
        self.life = 1
        self.name = 'weapon'
        #计算发射时子弹的位置
        if substance != None:
            self.rotation_position = substance.rotation_position      
            heading_x = cos(self.rotation_position * pi / 180.)
            heading_y = sin(self.rotation_position * pi / 180.)
            heading = Vector2(heading_x, -heading_y)
            self.position = substance.position.copy()  + heading * substance.width#要有copy(),否则会改变tank的位置
        else:
            self.rotation_position = 90
            self.position = Vector2(random.randint(50, WorldBridge.proManager.FIRELENGTH  - 50), random.randint(50, WorldBridge.proManager.FIREHEIGHT - 50))
    def out_of_border(self):#出界后自动删除
        if self.next_position.x < 0 or self.next_position.x >WorldBridge.proManager.FIRELENGTH or self.next_position.y < 0 or self.next_position.y > WorldBridge.proManager.FIREHEIGHT:
                self.die()
                
class SimpleShell(Weapon):      #普通子弹
    def __init__(self, substance):
        Weapon.__init__(self, substance, simple_shell_image)#注意图片不要传错了 
        self.name = '普通子弹'
        self.ap = 3
        self.speed =  8 #substance.speed * 2      
class IceShell(Weapon):            #冰冻子弹
    def __init__(self, substance):
        Weapon.__init__(self, substance, ice_shell_image)
        self.name = '冰冻弹'
        self.ap = 2
        self.speed = 5   
    def effect(self, effect_object):
        effect_object.statu = 1#冰冻
        Substance.effect(self, effect_object)      
class SpecialShell(Weapon):         #威力无穷的子弹
    '''威力无穷的子弹'''
    def __init__(self, substance):
        Weapon.__init__(self, substance, specila_shell_image)
        self.name = '毒气弹'
        self.ap = 0
        self.speed =  5   
    def effect(self, effect_object):
        effect_object.statu = 2
        effect_object.last_time = 4
        Substance.effect(self, effect_object)
class Mine(Weapon):                 #地雷
    def __init__(self, substance):
        Weapon.__init__(self, substance, mine_image)
        self.name = '地雷'
        self.speed = 0
        self.ap = 6
        self.rotation_position = self.rotation_position + 180 # +180指向坦克炮筒方向
        move_x = cos(self.rotation_position * pi / 180)
        move_y = sin(self.rotation_position * pi / 180)  
        self.position = substance.next_position.copy()  + (move_x * substance.width, - move_y * substance.height)#要有copy(),否则会改变tank的位置  
class Tracking(Weapon):             #精确弹
    def __init__(self, substance):
        Weapon.__init__(self, substance, tracking_image)
        self.name = '追踪弹'
        self.ap = 2
        self.speed = 4.0
        if len(WorldBridge.proManager.monsters) != 0:
            self.aim = WorldBridge.proManager.monsters[0]#追踪第一个怪兽
        #追踪任意怪兽的方向
        x = self.aim.position.x + self.aim.width / 4 - self.position.x
        y = -(self.aim.position.y + self.aim.height / 4 - self.position.y)
        self.rotation_position = atan2(y, x) / pi * 180
        
'''补给的彩蛋'''
#self.statu = 0  0代表正常状态, 1代表冰冻状态, 2代表中毒状态, 3加速, 4护甲, 5加血                   
class IceEgg(Weapon):
    def __init__(self, substance):
        Weapon.__init__(self, substance, egg_image)
        self.name = 'ice_egg'
        self.speed = 0
    def effect(self, effect_object):
        effect_object.statu = 1
        effect_object.last_time = 8
        Weapon.effect(self, effect_object)
class PoisonEgg(Weapon):
    def __init__(self, substance):
        Weapon.__init__(self, substance, egg_image)
        self.name = 'poison_egg'
        self.speed = 0
    def effect(self, effect_object):
        effect_object.statu = 2
        effect_object.last_time = 8
        Weapon.effect(self, effect_object)
class SpeedEgg(Weapon):
    def __init__(self, substance):
        Weapon.__init__(self, substance, egg_image)
        self.name = 'speed_egg'
        self.speed = 0
    def effect(self, effect_object):
        effect_object.statu = 3
        effect_object.last_time = 8
        Weapon.effect(self, effect_object)
class ArmorEgg(Weapon):
    def __init__(self, substance):
        Weapon.__init__(self, substance, egg_image)
        self.name = 'armor_egg'
        self.speed = 0
    def effect(self, effect_object):
        effect_object.statu = 4
        effect_object.last_time = 8
        Weapon.effect(self, effect_object)
class LifeEgg(Weapon):
    def __init__(self, substance):
        Weapon.__init__(self, substance, egg_image)
        self.name = 'life_egg'
        self.speed = 0
    def effect(self, effect_object):
        effect_object.statu = 5
        effect_object.last_time = 8
        Weapon.effect(self, effect_object)
        