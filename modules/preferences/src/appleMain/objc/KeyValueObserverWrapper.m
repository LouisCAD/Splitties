// Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.

#import "KeyValueObserverWrapper.h"
#import "KeyValueObserver.h"


@interface Helper : NSObject
@property(nonatomic, readonly) NSString *targetKeyPath;
@property(nonatomic, weak) NSObject *object;
@property(nonatomic, readonly) id <KeyValueObserver> observer;

- (instancetype)initWithTargetKeyPath:(NSString *)targetKeyPath object:(NSObject *)object observer:(id <KeyValueObserver>)observer options:(NSKeyValueObservingOptions)options;

- (void)invalidate;
@end


@interface KeyValueObserverWrapper ()
@property(nonatomic, readonly) Helper *helper;
@end


@implementation KeyValueObserverWrapper

- (instancetype)initWithTargetKeyPath:(NSString *)targetKeyPath object:(NSObject *)object observer:(id <KeyValueObserver>)observer options:(NSKeyValueObservingOptions)options {
    self = [super init];
    if (self) {
        _helper = [[Helper alloc] initWithTargetKeyPath:targetKeyPath object:object observer:observer options:options];
    }
    return self;
}

- (void)invalidate {
    [_helper invalidate];
}

- (void)dealloc {
    [self invalidate];
}
@end


@implementation Helper
- (instancetype)initWithTargetKeyPath:(NSString *)targetKeyPath object:(NSObject *)object observer:(id <KeyValueObserver>)observer options:(NSKeyValueObservingOptions)options {
    self = [super init];
    if (self) {
        _targetKeyPath = targetKeyPath;
        _object = object;
        _observer = observer;
        [object addObserver:self forKeyPath:targetKeyPath options:options context:nil];
    }
    return self;
}


// Inspired by https://github.com/apple/swift/blob/529d784329b56ab620bc36bf8465d54b1b4e17c2/stdlib/public/Darwin/Foundation/NSObject.swift#L207
- (void)observeValueForKeyPath:(nullable NSString *)keyPath ofObject:(nullable id)object change:(nullable NSDictionary<NSKeyValueChangeKey, id> *)change context:(nullable void *)context {
    id theObject = object;
    if (theObject == nil || theObject != _object) return;
    [_observer observeValueForKeyPath:keyPath ofObject:theObject change:change context:context];
}

- (void)invalidate {
    NSObject *object = self.object;
    if (!object) return;
    [object removeObserver:self forKeyPath:_targetKeyPath context:nil];
}
@end
