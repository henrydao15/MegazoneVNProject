package com.megazone.core.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author redis,
 */
@Service
@Slf4j
public class RedisImpl implements Redis {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	//key

	private String appendKeyPrefix(Object key) {
		if (key instanceof String) {
			return ((String) key);
		}
		return key.toString();
	}

	/**
	 * @param keys keys
	 */
	@Override
	public void del(Object... keys) {
		List<String> keysList = new ArrayList<>();
		for (Object key : keys) {
			keysList.add(appendKeyPrefix(key));
		}
		redisTemplate.delete(keysList);
	}

	/**
	 * @param key key
	 * @return time
	 */
	@Override
	public Long ttl(String key) {
		return redisTemplate.getExpire(appendKeyPrefix(key));
	}

	/**
	 * @param key     key
	 * @param timeout
	 */
	@Override
	public void expire(String key, Integer timeout) {
		redisTemplate.expire(appendKeyPrefix(key), timeout, TimeUnit.SECONDS);
	}


	/**
	 * @param key key
	 */
	@Override
	public void persist(String key) {
		redisTemplate.persist(appendKeyPrefix(key));
	}

	/**
	 * @param key key
	 */
	@Override
	public boolean exists(String key) {
		Boolean exists = redisTemplate.hasKey(appendKeyPrefix(key));
		return exists != null ? exists : false;
	}

	/**
	 * @param key key
	 * @return DataType
	 */
	@Override
	public DataType getType(String key) {
		return redisTemplate.type(appendKeyPrefix(key));
	}


	/**
	 * set
	 *
	 * @param key   key
	 * @param value value
	 */
	@Override
	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(appendKeyPrefix(key), value);
	}

	/**
	 * set
	 *
	 * @param key    key
	 * @param value  value
	 * @param second
	 */
	@Override
	public void setex(Object key, Integer second, Object value) {
		redisTemplate.opsForValue().set(appendKeyPrefix(key), value, second, TimeUnit.SECONDS);
	}

	/**
	 * @param key   key
	 * @param value value
	 * @return boolean true，
	 */
	@Override
	public Boolean setNx(String key, Long timeout, Object value) {
		return redisTemplate.opsForValue().setIfAbsent(appendKeyPrefix(key), value, timeout, TimeUnit.SECONDS);
	}


	/**
	 * @param key key
	 * @return value
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key) {
		return (T) redisTemplate.opsForValue().get(appendKeyPrefix(key));
	}

	/**
	 * @param keys keys
	 * @return values
	 */
	@Override
	public List<Object> mGet(Object... keys) {
		List<String> keysList = new ArrayList<>();
		for (Object key : keys) {
			keysList.add(appendKeyPrefix(key));
		}
		return redisTemplate.opsForValue().multiGet(keysList);
	}

	/**
	 * @param map map
	 */
	@Override
	public void mSet(Map<String, Object> map) {
		for (String key : map.keySet()) {
			Object value = map.remove(key);
			map.put(appendKeyPrefix(key), value);
		}
		redisTemplate.opsForValue().multiSet(map);
	}

	/**
	 * @param map map
	 */
	@Override
	public void mSetNx(Map<String, Object> map) {
		for (String key : map.keySet()) {
			Object value = map.remove(key);
			map.put(appendKeyPrefix(key), value);
		}
		redisTemplate.opsForValue().multiSetIfAbsent(map);
	}


	/**
	 * @param key   key
	 * @param value
	 */
	@Override
	public void appendStr(Object key, String value) {
		redisTemplate.opsForValue().append(appendKeyPrefix(key), value);
	}


	/**
	 * @param key      key
	 * @param hashKeys hashKeys
	 */
	@Override
	public void hdel(Object key, Object... hashKeys) {
		redisTemplate.opsForHash().delete(appendKeyPrefix(key), hashKeys);
	}

	/**
	 * @param key     key
	 * @param hashKey mapkey
	 * @param value   mapvalue
	 */
	@Override
	public void put(Object key, Object hashKey, Object value) {
		redisTemplate.opsForHash().put(appendKeyPrefix(key), hashKey, value);
	}


	/**
	 * @param key key
	 * @param map map
	 */
	@Override
	public void putAll(String key, Map<Object, Object> map) {
		redisTemplate.opsForHash().putAll(appendKeyPrefix(key), map);
	}

	/**
	 * @param key key
	 * @return map
	 */
	@Override
	public Map<Object, Object> getRedisMap(String key) {
		return redisTemplate.opsForHash().entries(appendKeyPrefix(key));
	}

	/**
	 * @param key key
	 * @return value
	 */
	@Override
	public List<Object> getValues(Object key) {
		return redisTemplate.opsForHash().values(appendKeyPrefix(key));
	}

	/**
	 * @param key     key
	 * @param hashKey hashKey
	 */
	@Override
	public Boolean hashMapKey(Object key, String hashKey) {
		return redisTemplate.opsForHash().hasKey(appendKeyPrefix(key), hashKey);
	}

	/**
	 * @param key   key
	 * @param value value
	 */
	@Override
	public void lpush(String key, Object value) {
		redisTemplate.opsForList().leftPush(appendKeyPrefix(key), value);
	}

	/**
	 * @param key   key
	 * @param value value
	 */
	@Override
	public void rpush(String key, Object value) {
		redisTemplate.opsForList().rightPush(appendKeyPrefix(key), value);
	}


	/**
	 * @param key key
	 * @return value
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T lPop(String key) {
		return (T) redisTemplate.opsForList().leftPop(appendKeyPrefix(key));
	}

	/**
	 * @param key key
	 * @return value
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T rPop(String key) {
		return (T) redisTemplate.opsForList().rightPop(appendKeyPrefix(key));
	}


	/**
	 * @param key   key
	 * @param index index
	 * @return value
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getKeyIndex(String key, int index) {
		return (T) redisTemplate.opsForList().index(appendKeyPrefix(key), index);
	}

	/**
	 * @param key key
	 * @return length
	 */
	@Override
	public Long getLength(String key) {
		return redisTemplate.opsForList().size(appendKeyPrefix(key));
	}

	/**
	 * @param key   key
	 * @param start
	 * @param end
	 * @return values
	 */
	@Override
	public List<Object> range(String key, int start, int end) {
		return redisTemplate.opsForList().range(appendKeyPrefix(key), start, end);
	}


	//set

	/**
	 * @param key    key
	 * @param values values
	 */
	@Override
	public void addSet(String key, Object... values) {
		redisTemplate.opsForSet().add(appendKeyPrefix(key), values);
	}

	/**
	 * @param key key
	 * @return value
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getSet(String key) {
		return (T) redisTemplate.opsForSet().pop(appendKeyPrefix(key));
	}

	/**
	 * @param key key
	 * @return values
	 */
	@Override
	public Set<Object> getSets(String key) {
		return redisTemplate.opsForSet().members(appendKeyPrefix(key));
	}


	/**
	 * @param key key
	 * @return length
	 */
	@Override
	public Long getSetsNum(String key) {
		return redisTemplate.opsForSet().size(appendKeyPrefix(key));
	}

	/**
	 * @param key key
	 * @return values
	 */
	@Override
	public Set<Object> members(String key) {
		return redisTemplate.opsForSet().members(appendKeyPrefix(key));
	}


	//zSet

	/**
	 * <p>
	 * <p>
	 * 1.set
	 * Set<ZSetOperations.TypedTuple<Object>> sets=new HashSet<>();
	 * 2.
	 * ZSetOperations.TypedTuple<Object> objectTypedTuple1 = new DefaultTypedTuple<Object>(value,，);
	 * 4.set
	 * sets.add(objectTypedTuple1);
	 * 5.
	 * reidsImpl.Zadd("zSet", list);
	 *
	 * @param key    key
	 * @param tuples tuples
	 */
	@Override
	public void zadd(String key, Set<ZSetOperations.TypedTuple<Object>> tuples) {
		redisTemplate.opsForZSet().add(appendKeyPrefix(key), tuples);
	}


	/**
	 * @param key key
	 * @param min min
	 * @param max max
	 * @return values
	 */
	@Override
	public Set<Object> reverseRange(String key, Double min, Double max) {
		return redisTemplate.opsForZSet().reverseRangeByScore(appendKeyPrefix(key), min, max);
	}

}
