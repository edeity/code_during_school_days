#_*_coding:utf-8_*_
'''
Created on 2013��9��22��

@author: javer
'''
'''���ӵ����������'''
from substance import *
import image_factory
#    --------------------------------���ǻ����ķָ���------------------------------------------------------------------------------------
'''ͼƬ������'''
image_factory = image_factory.ImageFactory()
'''����ͼƬ�ļ�'''
shell_image_filename = 'image/weapons/simpleshell.png'
ice_shell_image_filename = 'image/weapons/iceShell.png'
mine_image_filename = 'image/weapons/mine.png'
special_shell_image_filename = 'image/weapons/specialShell.png'
tracking_image_filename = 'image/weapons/tracking.png'
'''װ���ӵ�ͼƬ'''
simple_shell_image = image_factory.image_process(shell_image_filename, 7)
ice_shell_image = image_factory.image_process(ice_shell_image_filename, 1)
mine_image = image_factory.image_process(mine_image_filename, 4)
specila_shell_image = image_factory.image_process(special_shell_image_filename, 1)
tracking_image =  image_factory.image_process(tracking_image_filename, 4)
#egg
egg_image_filename = 'image/weapons/eggs/egg.png'
egg_image =  image_factory.image_process(egg_image_filename, 4)
#    --------------------------------���ǻ����ķָ���------------------------------------------------------------------------------------
class  WorldBridge():
    proManager = None
    Substance = None
    def __init__(self, proManager):
            WorldBridge.proManager = proManager   
            
class Weapon(Substance):    #��������,����������û���˺�
    def __init__(self, substance, images):
        Substance.__init__(self, WorldBridge.proManager.weapons, images)
        self.ap = 1
        self.life = 1
        self.name = 'weapon'
        #���㷢��ʱ�ӵ���λ��
        if substance != None:
            self.rotation_position = substance.rotation_position      
            heading_x = cos(self.rotation_position * pi / 180.)
            heading_y = sin(self.rotation_position * pi / 180.)
            heading = Vector2(heading_x, -heading_y)
            self.position = substance.position.copy()  + heading * substance.width#Ҫ��copy(),�����ı�tank��λ��
        else:
            self.rotation_position = 90
            self.position = Vector2(random.randint(50, WorldBridge.proManager.FIRELENGTH  - 50), random.randint(50, WorldBridge.proManager.FIREHEIGHT - 50))
    def out_of_border(self):#������Զ�ɾ��
        if self.next_position.x < 0 or self.next_position.x >WorldBridge.proManager.FIRELENGTH or self.next_position.y < 0 or self.next_position.y > WorldBridge.proManager.FIREHEIGHT:
                self.die()
                
class SimpleShell(Weapon):      #��ͨ�ӵ�
    def __init__(self, substance):
        Weapon.__init__(self, substance, simple_shell_image)#ע��ͼƬ��Ҫ������ 
        self.name = '��ͨ�ӵ�'
        self.ap = 3
        self.speed =  8 #substance.speed * 2      
class IceShell(Weapon):            #�����ӵ�
    def __init__(self, substance):
        Weapon.__init__(self, substance, ice_shell_image)
        self.name = '������'
        self.ap = 2
        self.speed = 5   
    def effect(self, effect_object):
        effect_object.statu = 1#����
        Substance.effect(self, effect_object)      
class SpecialShell(Weapon):         #����������ӵ�
    '''����������ӵ�'''
    def __init__(self, substance):
        Weapon.__init__(self, substance, specila_shell_image)
        self.name = '������'
        self.ap = 0
        self.speed =  5   
    def effect(self, effect_object):
        effect_object.statu = 2
        effect_object.last_time = 4
        Substance.effect(self, effect_object)
class Mine(Weapon):                 #����
    def __init__(self, substance):
        Weapon.__init__(self, substance, mine_image)
        self.name = '����'
        self.speed = 0
        self.ap = 6
        self.rotation_position = self.rotation_position + 180 # +180ָ��̹����Ͳ����
        move_x = cos(self.rotation_position * pi / 180)
        move_y = sin(self.rotation_position * pi / 180)  
        self.position = substance.next_position.copy()  + (move_x * substance.width, - move_y * substance.height)#Ҫ��copy(),�����ı�tank��λ��  
class Tracking(Weapon):             #��ȷ��
    def __init__(self, substance):
        Weapon.__init__(self, substance, tracking_image)
        self.name = '׷�ٵ�'
        self.ap = 2
        self.speed = 4.0
        if len(WorldBridge.proManager.monsters) != 0:
            self.aim = WorldBridge.proManager.monsters[0]#׷�ٵ�һ������
        #׷��������޵ķ���
        x = self.aim.position.x + self.aim.width / 4 - self.position.x
        y = -(self.aim.position.y + self.aim.height / 4 - self.position.y)
        self.rotation_position = atan2(y, x) / pi * 180
        
'''�����Ĳʵ�'''
#self.statu = 0  0��������״̬, 1�������״̬, 2�����ж�״̬, 3����, 4����, 5��Ѫ                   
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
        