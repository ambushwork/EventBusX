官方EventBus的改進：
* 发布粘性事件：触发了所有同类型订阅方法
* 粘性事件订阅方法无法二次消费
* 多次调用和移除粘性事件时，post会执行多次粘性事件订阅方法
* 优化索引，让api更简单直接
* 重写注解处理器，apt+javapoet
* 弱化线程池，使用缓存线程池代替
* 移除对象池的概念，考虑recycle问题。
* 修复sbuscription对象匹配bug，删除hashCode无用方法
* 纯反射技术完全剥离，之前项目有完整详细介绍。